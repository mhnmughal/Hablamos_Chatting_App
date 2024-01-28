package hablamos.view;

import java.io.IOException;
import hablamos.controller.UserManager;
import hablamos.network.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController {
	@FXML
	private Stage stage;
	private Scene scene;
	private Parent root;
	private UserManager controller;
	
	
	@FXML
	private TextField username;
	@FXML
	private TextField id;
	@FXML
	private TextField status;
	@FXML
	private TextField password;
	private NetworkManager networkManager;
	public SignUpController()
	{
		 networkManager=new NetworkManager();
	}
    
    @FXML
    public void toBack(ActionEvent e)
    {
    	try {
    		 root= FXMLLoader.load(getClass().getResource("Login.fxml"));
    		stage=(Stage)((Node)e.getSource()).getScene().getWindow();
    		scene=new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	} catch (IOException e1) {
    		
    		e1.printStackTrace();
    	}
    }
    @FXML
    public void AddUser(ActionEvent e)
    {
    	String ipAddress="127.0.0.1"; 
    	controller=new UserManager();
    	controller.createUser(Integer.parseInt(id.getText()) ,username.getText(), password.getText());
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("User Added In Database");
    	alert.setHeaderText("Username: "+username.getText()+ "  is added successfully!");
    	alert.setContentText("If You click on Ok button then you will go to the Login page directly");
    	if(alert.showAndWait().get()== ButtonType.OK)
    	{
    		 //networkManager.registerUser(Integer.parseInt(id.getText()), ipAddress);
    		try {
       		 root= FXMLLoader.load(getClass().getResource("Login.fxml"));
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
