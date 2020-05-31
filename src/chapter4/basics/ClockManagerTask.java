package chapter4.basics;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Presenter */
class TickerTask extends Task<Long> {
	private final static long UPDATE_INTERVAL = 10; // Milliseconds

	private Clock clock;

	public TickerTask(Clock clock) {
		this.clock = clock;
	}

	protected Long call() {
		while (!isCancelled()) {
			updateValue(clock.getTime());
			try {
				Thread.sleep(UPDATE_INTERVAL);
			} catch (InterruptedException e) {
			}
		}
		return clock.getTime();
	}
}

class ClockPresenterTask extends ClockPresenter {
	private TickerTask task;

	public void start() {
		if (task == null) {
			clock.reset();
			task = new TickerTask(clock);
			task.valueProperty().addListener((obs, oldVal, newVal) -> view.showTime(newVal));
			Thread thread = new Thread(task);
			thread.setDaemon(true);
			thread.start();
		}
	}

	public void stop() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}
}

/* Main */
public class ClockManagerTask extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		ClockPresenterTask p = new ClockPresenterTask();
		ClockView view = new ClockView(p);
		Clock clock = new Clock();
		p.setModelAndView(clock, view);
		p.reset();

		Scene scene = new Scene(view.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Stoppuhr mit Task");
		primaryStage.show();
	}
}
