package ru.petrelevich.services;

import ru.petrelevich.domain.Message;

public interface MessageService {
    void handleMessage(String roomId, Message message,String topicTemplate);
    void loadRoomMessages(String roomId, String simpDestination);
}
