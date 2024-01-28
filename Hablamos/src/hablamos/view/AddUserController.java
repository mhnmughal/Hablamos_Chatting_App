package hablamos.view;
import javafx.fxml.FXML;
import hablamos.controller.UserManager;
import hablamos.network.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddUserController {

    @FXML
    private TextField addUsername;

    @FXML
    private TextField addId;

    @FXML
    private Button addUserButton;

    // Reference to the main controller
    private ChatAppController mainController;
    private UserManager userManager;
    private NetworkManager networkManager;
    public void setMainController(ChatAppController mainController) {
        this.mainController = mainController;
    }
public AddUserController()
 	{
	userManager=new UserManager();
	networkManager = new NetworkManager();
	}
    @FXML 
    private void onAddUser() {
        String username = addUsername.getText();
        String userId = addId.getText();
        String ipAddress="127.0.0.1 ";
        if (!username.isEmpty() && !userId.isEmpty()) {
            // Add the user to the main user list
            mainController.addUserToList(username);
            userManager.AddUser(Integer.parseInt(userId), username);
            // Register the user with the server
            //networkManager.registerUser(Integer.parseInt(userId), ipAddress);
            // Close the add user window
            addUserButton.getScene().getWindow().hide();
        }
    }
}
