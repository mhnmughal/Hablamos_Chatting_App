package hablamos.view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class UserViewController  {

    @FXML
    private Label name;

    @FXML
    private ScrollPane userMsg_sp;

    @FXML
    private TextFlow textFlowUser;

    @FXML
    private TextField text;

    @FXML
    private Button send;

    private ChatAppController chatAppController;
    private String selectedUser;

    @FXML
    public void initialize() {
    	 text.setOnKeyPressed(event -> {
             if (event.getCode() == KeyCode.ENTER) {
                 onSendMessage(); 
             }
         });
    	 textFlowUser = (TextFlow) userMsg_sp.getContent();
    }

    public void setChatAppController(ChatAppController chatAppController) {
        this.chatAppController = chatAppController;
    }
    

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
        name.setText(selectedUser);
    }

    public void onSendMessage() {
        String message = text.getText();
        if (!message.isEmpty()) {
            chatAppController.displayMessage( selectedUser+" to You  "  + ": " + message, false);
            //chatAppController.sendMessageToUser(selectedUser, message);
            displayMessage(" You to User: " +message, true);
       
        }
        text.clear();
    }

   

    public void displayMessage(String message, boolean isSentByCurrentUser) {
        if (textFlowUser != null) {
            Text textNode = new Text(message + "\n");
            if (isSentByCurrentUser) {
                textNode.setStyle("-fx-fill: green; -fx-font-weight: bold; -fx-text-alignment: right;");
            } else {
                textNode.setStyle("-fx-fill: blue; -fx-font-weight: bold; -fx-text-alignment: left;");
            }
            textFlowUser.getChildren().add(textNode); 
            userMsg_sp.layout();
            userMsg_sp.setVvalue(1.0); 
        }
    }
    public void displayReceivedMessageFromChatApp(String message) {
    	 
    	displayMessage("ChatApp to You: " + message, false); 
    }

	
}
