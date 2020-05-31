package chapter4.basics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Counter extends Application {
	private Label label;
	private int counter;

	public static void main(String[] args) {
		launch(args);
	}

	private void increment() {
		counter++;
		label.setText("" + counter);
	}

	private void reset() {
		counter = 0;
		label.setText("" + counter);
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
		root.setAlignment(Pos.BOTTOM_RIGHT);
		root.setFillWidth(true);
		VBox.setVgrow(b1, Priority.ALWAYS);
		b1.setMaxHeight(Double.MAX_VALUE);
		b2.setMaxWidth(Double.MAX_VALUE);
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
