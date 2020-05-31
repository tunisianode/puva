package chapter4.basics;

import chapter2.GroupTree;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFXThreads extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		System.out.println("start: thread = " + Thread.currentThread().getName());
		GroupTree.dumpAll();
		Platform.exit();
	}
}
