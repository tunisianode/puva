package chapter6.counter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;
import java.util.function.Consumer;

interface RMISupplier<T> {
	public T execute() throws RemoteException;
}

class ClientGuiView {
	private ClientGuiPresenter presenter;
	private VBox root;
	private Label label;

	public ClientGuiView(ClientGuiPresenter presenter) {
		this.presenter = presenter;
		initView();
	}

	private void initView() {
		root = new VBox(10);
		Insets ins = new Insets(10);
		root.setPadding(ins);

		label = new Label();
		label.setMaxWidth(Double.MAX_VALUE);
		root.getChildren().add(label);
		Button bInc = new Button("Erh�hen");
		bInc.setMaxWidth(Double.MAX_VALUE);
		bInc.setOnAction(e -> presenter.increment());
		root.getChildren().add(bInc);
		Button bReset = new Button("Zur�cksetzen");
		bReset.setMaxWidth(Double.MAX_VALUE);
		bReset.setOnAction(e -> presenter.reset());
		root.getChildren().add(bReset);
	}

	public Pane getUI() {
		return root;
	}

	public void showCounter(int counter) {
		label.setText("" + counter);
	}
}

class ClientGuiPresenter {
	private Counter model;
	private ClientGuiView view;

	public void setModelAndView(Counter model, ClientGuiView view) {
		this.model = model;
		this.view = view;
	}

	public void increment() {
		asyncCall(() -> model.increment(), (c) -> view.showCounter(c));
	}

	public void reset() {
		asyncCall(() -> model.reset(), (c) -> view.showCounter(c));
	}

	private <T> void asyncCall(RMISupplier<T> rmiCall, Consumer<T> fxCall) {
		new Thread(() -> doInThread(rmiCall, fxCall)).start();
	}

	private <T> void doInThread(RMISupplier<T> rmiCall, Consumer<T> fxCall) {
		try {
			T t = rmiCall.execute();
			Platform.runLater(() -> fxCall.accept(t));
		} catch (RemoteException e) {
			System.err.println("Es gibt Probleme mit RMI!");
		}
	}
}

public class ClientGui extends Application {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Argumente: Server RMI-Objekt");
			Platform.exit();
			return;
		}
		launch(args);
	}

	public void start(Stage primaryStage) {
		List<String> args = getParameters().getUnnamed();
		Counter m = null;
		try {
			m = (Counter) Naming.lookup("rmi://" +
					args.get(0) + "/" +
					args.get(1));
		} catch (Exception e) {
			System.out.println("Verbindung zu Server nicht m�glich");
			Platform.exit();
		}

		ClientGuiPresenter p = new ClientGuiPresenter();
		ClientGuiView v = new ClientGuiView(p);
		p.setModelAndView(m, v);

		Scene scene = new Scene(v.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle("RMI-Z�hler");
		primaryStage.show();
	}
}
