package chapter4.basics;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/* Model */
class Clock {
	private long startTime;

	public synchronized long getTime() {
		return System.currentTimeMillis() - startTime;
	}

	public synchronized void reset() {
		startTime = System.currentTimeMillis();
	}
}

/* Presenter */
class TickerThread extends Thread {
	private final static long UPDATE_INTERVAL = 10; // Milliseconds

	private Clock clock;

	private ClockView view;

	public TickerThread(Clock clock, ClockView view) {
		this.clock = clock;
		this.view = view;
		setDaemon(true);
		start();
	}

	public void run() {
		try {
			while (!isInterrupted()) {
				Platform.runLater(() -> view.showTime(clock.getTime()));
				Thread.sleep(UPDATE_INTERVAL);
			}
		} catch (InterruptedException e) {
		}
	}
}

class ClockPresenter {
	protected Clock clock;

	protected ClockView view;

	private TickerThread thread;

	public void setModelAndView(Clock clock, ClockView view) {
		this.clock = clock;
		this.view = view;
	}

	public void start() {
		if (thread == null) {
			clock.reset();
			thread = new TickerThread(clock, view);
		}
	}

	public void stop() {
		if (thread != null) {
			thread.interrupt();
			thread = null;
		}
	}

	public void reset() {
		clock.reset();
		view.showTime(clock.getTime());
	}

	public void exit() {
		Platform.exit();
	}
}

/* View */
class ClockView {
	private ClockPresenter presenter;

	private VBox root;

	private Label timeLabel;

	public ClockView(ClockPresenter presenter) {
		this.presenter = presenter;
		initView();
	}

	private void initView() {
		root = new VBox(8);
		root.setPadding(new Insets(10));
		timeLabel = new Label();
		root.getChildren().add(timeLabel);
		Button b1 = new Button("Start");
		b1.setOnAction(e -> presenter.start());
		b1.setMaxWidth(Double.MAX_VALUE);
		root.getChildren().add(b1);
		Button b2 = new Button("Stopp");
		b2.setOnAction(e -> presenter.stop());
		b2.setMaxWidth(Double.MAX_VALUE);
		root.getChildren().add(b2);
		Button b3 = new Button("Null");
		b3.setOnAction(e -> presenter.reset());
		b3.setMaxWidth(Double.MAX_VALUE);
		root.getChildren().add(b3);
		Button b4 = new Button("Ende");
		b4.setMaxWidth(Double.MAX_VALUE);
		b4.setOnAction(e -> presenter.exit());
		root.getChildren().add(b4);
	}

	public Pane getUI() {
		return root;
	}

	public void showTime(long elapsedTime) {
		long seconds = elapsedTime / 1000;
		long milliSecs = elapsedTime % 1000;
		String prefix;
		if (milliSecs < 10) {
			prefix = "00";
		} else if (milliSecs < 100) {
			prefix = "0";
		} else {
			prefix = "";
		}
		timeLabel.setText(seconds + ":" + prefix + milliSecs);
	}
}

/* Main */
public class ClockManager extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		ClockPresenter p = new ClockPresenter();
		ClockView view = new ClockView(p);
		Clock clock = new Clock();
		p.setModelAndView(clock, view);
		p.reset();

		Scene scene = new Scene(view.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Stoppuhr");
		primaryStage.show();
	}
}
