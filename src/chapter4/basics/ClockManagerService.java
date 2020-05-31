package chapter4.basics;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.stage.Stage;

/* Presenter */
class TickerService extends Service<Long> {
	private Clock clock;

	public TickerService(Clock clock) {
		this.clock = clock;
	}

	protected Task<Long> createTask() {
		return new TickerTask(clock);
	}
}

class ClockPresenterService extends ClockPresenter {
	private TickerService service;

	public void setModelAndView(Clock clock, ClockView view) {
		super.setModelAndView(clock, view);
		service = new TickerService(clock);
		service.valueProperty().addListener((obs, oldVal, newVal) -> show(newVal));
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
public class ClockManagerService extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		ClockPresenterService p = new ClockPresenterService();
		ClockView view = new ClockView(p);
		Clock clock = new Clock();
		p.setModelAndView(clock, view);
		p.reset();

		Scene scene = new Scene(view.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Stoppuhr mit Service");
		primaryStage.show();
	}
}
