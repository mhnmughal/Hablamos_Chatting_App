package hablamos.view;

import java.io.IOException;
import hablamos.model.User;
import hablamos.controller.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	private UserManager controller;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	
    // Sample method to navigate to Customer Management screen
    @FXML
    public void toSignUp(ActionEvent e)
    {
    	try {
    		 root= FXMLLoader.load(getClass().getResource("SignUp.fxml"));
    		stage=(Stage)((Node)e.getSource()).getScene().getWindow();
    		scene=new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	} catch (IOException e1) {
    		
    		e1.printStackTrace();
    	}
    }
    @FXML
    public void toLogin(ActionEvent e)
    {
    	String Username=username.getText();
    	String Password=password.getText();
    	
    	if(isValidUser(Username,Password))
    	{
    		Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Congratulations ");
            alert.setHeaderText("The entered username or password is correct.");
            alert.setContentText("If You Click on the ok button then You will land on the The EnergyManagement System page");
            if(alert.showAndWait().get()== ButtonType.OK)
            {
            	try {
           		 root= FXMLLoader.load(getClass().getResource("ChatApp.fxml"));
           		stage=(Stage)((Node)e.getSource()).getScene().getWindow();
           		scene=new Scene(root);
           		stage.setScene(scene);
           		stage.show();
           	} catch (IOException e1) {
           		
           		e1.printStackTrace();
           	}
            }
    	}
    	else 
    	{
    		Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Credentials");
            alert.setHeaderText("The entered username or password is incorrect.");
            alert.setContentText("If You Click on the ok button then You will land on the SignUp page");
            if(alert.showAndWait().get()== ButtonType.OK)
            {
            	try {
           		 root= FXMLLoader.load(getClass().getResource("SignUp.fxml"));
           		stage=(Stage)((Node)e.getSource()).getScene().getWindow();
           		scene=new Scene(root);
           		stage.setScene(scene);
           		stage.show();
           	} catch (IOException e1) {
           		
           		e1.printStackTrace();
           	}
            }
    	}
    	  
   }
    private boolean isValidUser(String enteredUsername, String enteredPassword) {
        controller = new UserManager();
        controller.GetUserDetail();

        for (User user : controller.getUserList()) {
            if (user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {
                return true;
            }
        }

        return false;
    }
}