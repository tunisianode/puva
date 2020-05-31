package chapter4.basics;

import javafx.application.Platform;

public class CounterDelayedWithThreads extends CounterDelayed {
	public static void main(String[] args) {
		launch(args);
	}

	protected void increment() {
		Thread t = new Thread(() -> incrementAsync());
		t.setDaemon(true);
		t.start();
	}

	private void incrementAsync() {
		for (int i = 1; i <= 10; i++) {
			Platform.runLater(() -> reallyIncrement());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	private void reallyIncrement() {
		counter++;
		String message = "" + counter;
		System.out.println(message);
		label.setText(message);
	}
}
