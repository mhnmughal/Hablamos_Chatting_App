package hablamos.network;

import hablamos.model.Message;

public interface MessageListener {
    void onMessageReceived(Message message);
}