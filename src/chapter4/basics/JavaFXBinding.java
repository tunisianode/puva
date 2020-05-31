package chapter4.basics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXBinding extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Label output = new Label();
		TextField input = new TextField();
		output.textProperty().bind(input.textProperty());
		VBox root = new VBox();
		root.getChildren().add(input);
		root.getChildren().add(output);

		Scene scene = new Scene(root, 240, 50);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Kopplung");
		primaryStage.show();
	}
}
