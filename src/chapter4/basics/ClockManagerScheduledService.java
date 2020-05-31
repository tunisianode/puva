package chapter4.basics;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/* Presenter */
class SingleTickerTask extends Task<Long> {
	private Clock clock;

	public SingleTickerTask(Clock clock) {
		this.clock = clock;
	}

	protected Long call() {
		//updateValue(clock.getTime());
		return clock.getTime();
	}
}

class ScheduledTickerService extends ScheduledService<Long> {
	private Clock clock;

	public ScheduledTickerService(Clock clock) {
		this.clock = clock;
	}

	protected Task<Long> createTask() {
		return new SingleTickerTask(clock);
	}
}

class ClockPresenterScheduledService extends ClockPresenter {
	private final static long UPDATE_INTERVAL = 10; // Milliseconds

	private ScheduledTickerService service;

	public void setModelAndView(Clock clock, ClockView view) {
		super.setModelAndView(clock, view);
		service = new ScheduledTickerService(clock);
		service.valueProperty().addListener((obs, oldVal, newVal) -> show(newVal));
		service.setPeriod(Duration.millis(UPDATE_INTERVAL));
	}

	private void show(Long newVal) {
		if (newVal != null) {
			view.showTime(newVal);
		}
	}

	public void start() {
		if (!service.isRunning()) {
			clock.reset();
			service.reset();
			service.start();
		}
	}

	public void stop() {
		service.cancel();
	}
}

/* Main */
public class ClockManagerScheduledService extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		ClockPresenterScheduledService p = new ClockPresenterScheduledService();
		ClockView view = new ClockView(p);
		Clock clock = new Clock();
		p.setModelAndView(clock, view);
		p.reset();

		Scene scene = new Scene(view.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Stoppuhr mit Scheduled Service");
		primaryStage.show();
	}
}
