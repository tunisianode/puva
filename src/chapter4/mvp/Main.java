package chapter4.mvp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		Presenter p = new Presenter();
		View v = new View(p);
		Model m = new Model();
		p.setModelAndView(m, v);
		p.choose();

		Scene scene = new Scene(v.getUI());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Vokabel-Training");
		primaryStage.show();
	}
}
