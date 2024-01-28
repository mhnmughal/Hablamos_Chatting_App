package hablamos.view;

import hablamos.controller.UserManager;
import hablamos.model.Message;
import hablamos.model.User;
import hablamos.network.MessageListener;
import hablamos.network.NetworkManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatAppController  {

    @FXML
    private ListView<String> listview;
    @FXML
    private TextField search;
    @FXML
    private Label name_label;
    @FXML
    private TextField status;
    @FXML
    private ScrollPane msg_sp;
    
    @FXML
    private TextField text_field;

    private FilteredList<String> filteredUsers;
    private NetworkManager networkManager;
    private ObservableList<String> availableUsers;
    private TextFlow textFlow;
    private UserManager userManager;
    private String selectedUser;
    private User currentUser; 
    private UserViewController userViewController;

    public ChatAppController() {
        this.availableUsers = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        userManager = new UserManager();
        userViewController= new UserViewController();
        loadExternalUsers();
        initializeNetworkManager();
        listview.setItems(availableUsers);
        User currentUser = userManager.getCurrentUser();
        userManager.setCurrentUser(currentUser);

        // Add a listener to the listview selection property
        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                name_label.setText(newValue);
                selectedUser = newValue;
             // Open the UserView when a username is selected
                openUserView(selectedUser);
                
            }
        });

        initializeSearch();

        text_field.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onSendMessage();
            }
        });

        textFlow = (TextFlow) msg_sp.getContent();
    }
    private void updateMessageInUserView(String message) {
        if (userViewController != null) {
            userViewController.displayReceivedMessageFromChatApp(message);
        }
    }
    // New method to initialize the NetworkManager
    private void initializeNetworkManager() {
        networkManager = new NetworkManager();
        
    

      
    }
    private void initializeSearch() {
        filteredUsers = new FilteredList<>(availableUsers, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return user.toLowerCase().contains(newValue.toLowerCase());
            });
        });

        SortedList<String> sortedUsers = new SortedList<>(filteredUsers);
        listview.setItems(sortedUsers);

        search.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleEnterKeyPress();
            }
        });
    }
    private void setUserViewController(UserViewController userViewController) {
        this.userViewController = userViewController;
    }
    private void handleEnterKeyPress() {
        String firstFilteredUser = filteredUsers.stream().findFirst().orElse(null);
        if (firstFilteredUser != null) {
            listview.getSelectionModel().select(firstFilteredUser);
        }
    }

    private void loadExternalUsers() {
        userManager.GetExternalUser();
        List<User> externalUsers = userManager.getUserList();
        for (User user : externalUsers) {
            addUserToList(user.getUsername());
        }
    }

    @FXML
    public void onSendMessage() {
        String message = text_field.getText();
        
        if (!message.isEmpty()) {
        	status.setText("Online");
        	
            
            
       
        	 getUserIdByName(selectedUser);
            sendMessageToUser(selectedUser, message);
            updateMessageInUserView(message);
           
        }
        text_field.clear();              
    }
    
    public void displayMessage(String message, boolean isSentByCurrentUser) {
        Text textNode = new Text(message + "\n");
        if (isSentByCurrentUser) {
            textNode.setStyle("-fx-fill: green; -fx-font-weight: bold; -fx-text-alignment: right;");
        } else {
            textNode.setStyle("-fx-fill: blue; -fx-font-weight: bold; -fx-text-alignment: left;");
        }
        textFlow.getChildren().add(textNode);
        msg_sp.layout();
        msg_sp.setVvalue(1.0);
    }
   
    public void addUserToList(String username) {
        availableUsers.add(username);
        listview.setItems(availableUsers);
    }

    @FXML
    private void addUserPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Adduser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add User");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            AddUserController addUserController = loader.getController();
            addUserController.setMainController(this);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 // Add this method to get the ID of the selected user
    private int getUserIdByName(String username) {
        List<User> users = userManager.getUserList();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user.getId();
            }
        }
        return -1;  // Return -1 if the user is not found
    }
    private void openUserView(String selectedUser) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("UserView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("User View - " + selectedUser);

            Scene scene = new Scene(root);
            stage.setScene(scene);

            UserViewController userViewController = loader.getController();
            userViewController.setChatAppController(this);
            userViewController.setSelectedUser(selectedUser);
            setUserViewController(userViewController);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessageToUser(String user, String messageContent) {
    	String targetIpAddress = getUserIpAddressByName(user); 

        // Create a Map to represent the message content
        Map<String, Object> messageContentMap = new HashMap<>();
        messageContentMap.put("sender", currentUser);
        messageContentMap.put("content", messageContent);

        // Use the new sendMessage method in NetworkManager
       // networkManager.sendMessage(targetIpAddress, messageContentMap);

        // Display the sent message
        String formattedMessage = "You to " + user + ": " + messageContent;
        
        displayMessage(formattedMessage, true);
       
    }
    private String getUserIpAddressByName(String username) {
        // You may need to maintain a map of usernames to IP addresses or another mechanism
        // to retrieve the IP address of the selected user.
        // For simplicity, you can use a predefined map or list for testing purposes.
        // This part depends on how you manage the IP addresses of users.
        return "127.0.0.1"; // Replace with your actual mechanism to get the IP address.
    }
}
