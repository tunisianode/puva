package chapter4.basics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SomeShapes extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Pane root = new Pane();
		drawShapes(root);

		primaryStage.setTitle("Einige Formen");
		primaryStage.setScene(new Scene(root, 250, 60));
		primaryStage.show();
	}

	private void drawShapes(Pane root) {
		Line line = new Line(40, 10, 10, 40);
		line.setStroke(Color.RED);
		line.setStrokeWidth(5);
		root.getChildren().add(line);

		Circle c1 = new Circle(60, 25, 20);
		c1.setStroke(null);
		c1.setFill(Color.GREEN);
		root.getChildren().add(c1);
		Circle c2 = new Circle(90, 25, 20);
		c2.setStroke(Color.RED);
		c2.setFill(null);
		c2.setStrokeWidth(5);
		root.getChildren().add(c2);

		Rectangle r1 = new Rectangle(120, 10, 40, 40);
		r1.setStroke(null);
		r1.setFill(Color.GREEN);
		root.getChildren().add(r1);
		Rectangle r2 = new Rectangle(170, 10, 40, 40);
		r2.setStroke(Color.RED);
		r2.setFill(null);
		r2.setStrokeWidth(5);
		root.getChildren().add(r2);
	}
}
