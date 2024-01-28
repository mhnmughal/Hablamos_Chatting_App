package hablamos;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hablamos_App extends Application{
	 @Override
	    public void start(Stage primaryStage) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Login.fxml"));
	            Parent root = loader.load();
	            
	            
	            primaryStage.setTitle("Hablamos");
	            primaryStage.setScene(new Scene(root, 900, 700));
	            primaryStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
}
