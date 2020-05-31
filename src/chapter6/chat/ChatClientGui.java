package chapter6.chat;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

class ChatClientGuiView {
	private ChatClientGuiPresenter presenter;
	private BorderPane root;
	private TextField input;
	private TextArea output;

	public ChatClientGuiView(ChatClientGuiPresenter presenter) {
		this.presenter = presenter;
		initView();
	}

	private void initView() {
		root = new BorderPane();
		Insets ins = new Insets(10);
		root.setPadding(ins);

		input = new TextField();
		input.setOnAction(e -> handleInput());
		root.setTop(input);
		BorderPane.setMargin(input, new Insets(8));
		output = new TextArea();
		output.setEditable(false);
		root.setCenter(output);
		BorderPane.setMargin(output, new Insets(8));
	}

	private void handleInput() {
		String message = input.getText();
		input.setText("");
		presenter.send(message);
	}

	public Pane getUI() {
		return root;
	}

	public void showMessage(String message) {
		output.appendText(message + "\n");
	}
}

class ChatClientGuiPresenter {
	private ChatServer model;
	private String name;

	public void setModelAndName(ChatServer model, String name) {
		this.model = model;
		this.name = name;
	}

	public void send(String message) {
		new Thread(() -> sendInThread(message)).start();
	}

	private void sendInThread(String message) {
		try {
			model.sendMessage(name, message);
		} catch (RemoteException e) {
			System.out.println("Es gibt Probleme mit RMI!");
		}
	}
}

public class ChatClientGui extends Application {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Argumente: Spitzname Server Chat-Gruppe");
			Platform.exit();
			return;
		}
		launch(args);
		System.exit(0);
	}

	public void start(Stage primaryStage) {
		List<String> args = getParameters().getUnnamed();
		String name = args.get(0);
		ChatClientGuiPresenter p = new ChatClientGuiPresenter();
		ChatClientGuiView v = new ChatClientGuiView(p);
		try {
			ChatServer m = (ChatServer) Naming.lookup("rmi://" +
					args.get(1) + "/" +
					args.get(2));
			ChatClient callback = new ChatClientImpl(name, v);
			if (!m.addClient(callback)) {
				System.out.println("Anmeldung beim Server gescheitert!");
				System.exit(0);
			}
			p.setModelAndName(m, name);
		} catch (Exception e) {
			System.out.println("Verbindung zu Server nicht m�glich!");
			System.exit(0);
		}

		Scene scene = new Scene(v.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle(name);
		primaryStage.show();
	}
}
