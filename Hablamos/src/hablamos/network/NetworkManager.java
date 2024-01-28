// NetworkManager.java
package hablamos.network;
import java.io.*;
import java.net.*;
import java.util.*;

import hablamos.model.Message;
import hablamos.model.User;
public class NetworkManager {
    private final Map<Integer, String> userIpAddresses; // Mapping user ID to IP address
    private final List<MessageListener> messageListeners;

    public NetworkManager() {
        userIpAddresses = new HashMap<>();
        messageListeners = new ArrayList<>();
    }

    // Method to register a user with an IP address
    public void registerUser(int userId, String ipAddress) {
        userIpAddresses.put(userId, ipAddress);
    }

    // Method to send a message to a specified user
    public void sendMessage(int targetUserId, Message message) {
        String targetIpAddress = userIpAddresses.get(targetUserId);
        if (targetIpAddress != null) {
            try (Socket socket = new Socket(targetIpAddress,5058);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

                // Send the message to the target user
                out.writeObject(message); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendMessage(String targetIpAddress, Map<String, Object> messageContent) {
        try (Socket socket = new Socket(targetIpAddress, 5058); 
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Send the message content to the target user
            out.writeObject(messageContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Method to listen for incoming messages
 // Method to listen for incoming messages
    public void startListening() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(12347)) {
                while (true) {
                    Socket socket = serverSocket.accept();
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    // Read the incoming object
                    Object receivedObject = in.readObject();

                    // Check the type of the received object
                    if (receivedObject instanceof Message) {
                        // If it's a Message object, notify listeners
                        notifyMessageListeners((Message) receivedObject);
                    } else if (receivedObject instanceof Map) {
                        // If it's a Map object, handle it accordingly
                        handleMapMessage((Map<?, ?>) receivedObject);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Method to handle Map messages
    private void handleMapMessage(Map<?, ?> messageContent) {
        // Extract relevant information from the Map
        User sender = (User) messageContent.get("sender");
        String content = (String) messageContent.get("content");

        // Create a Message object
        Message receivedMessage = new Message(sender, null, content);

        // Notify listeners about the received message
        notifyMessageListeners(receivedMessage);
    }


    // Method to add a message listener
    public void registerListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    // Method to notify all message listeners
    private void notifyMessageListeners(Message message) {
        for (MessageListener listener : messageListeners) {
            listener.onMessageReceived(message);
        }
    }
    public void sendMessageToUser(String targetIpAddress, User sender, String content) {
        // Create a simple Map to represent the message content
        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("sender", sender);
        messageContent.put("content", content);

        // Send the message to the target user
        sendMessage(targetIpAddress, messageContent);
    }
}