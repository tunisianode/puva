package chapter4.basics;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DrawingLines extends Application {
	private Pane graphicsPane;
	private double x, y;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		graphicsPane = new Pane();
		root.setCenter(graphicsPane);
		HBox hbox = new HBox(20);
		Button b = new Button("L�schen");
		hbox.getChildren().add(b);
		hbox.getChildren().add(new Label("Irgendein unwichtiger Text"));
		hbox.setPadding(new Insets(10));
		root.setBottom(hbox);

		graphicsPane.setOnMousePressed
				(
						e -> mousePressed(e.getX(), e.getY())
				);
		graphicsPane.setOnMouseDragged
				(
						e -> mouseDragged(e.getX(), e.getY())
				);
		b.setOnAction
				(
						e -> clear()
				);

		primaryStage.setTitle("Freihandzeichnen");
		primaryStage.setScene(new Scene(root, 400, 150));
		primaryStage.show();

		Rectangle clipRect = new Rectangle();
		clipRect.widthProperty().bind(graphicsPane.widthProperty());
		clipRect.heightProperty().bind(graphicsPane.heightProperty());
		graphicsPane.setClip(clipRect);
	}

	private void mousePressed(double newX, double newY) {
		x = newX;
		y = newY;
		mouseDragged(x, y);
	}

	private void mouseDragged(double newX, double newY) {
		graphicsPane.getChildren().add(new Line(x, y, newX, newY));
		x = newX;
		y = newY;
	}

	private void clear() {
		graphicsPane.getChildren().clear();
	}
}
