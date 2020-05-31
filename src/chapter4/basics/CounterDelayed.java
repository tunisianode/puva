package chapter4.basics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CounterDelayed extends Application {
	protected Label label;
	protected int counter;

	public static void main(String[] args) {
		launch(args);
	}

	protected void increment() {
		for (int i = 1; i <= 10; i++) {
			counter++;
			String message = "" + counter;
			System.out.println(message);
			label.setText(message);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
	}

	private void reset() {
		counter = 0;
		String message = "" + counter;
		System.out.println(message);
		label.setText(message);
	}

	public void start(Stage primaryStage) {
		label = new Label();
		Button b1 = new Button("Erh�hen");
		b1.setOnAction((ActionEvent e) -> increment());
		Button b2 = new Button("Zur�cksetzen");
		b2.setOnAction(e -> reset());
		VBox root = new VBox(10);
		Insets padding = new Insets(10);
		root.setPadding(padding);
		root.getChildren().add(label);
		root.getChildren().add(b1);
		root.getChildren().add(b2);
		reset();

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Z�hler");
		primaryStage.show();
	}
}
