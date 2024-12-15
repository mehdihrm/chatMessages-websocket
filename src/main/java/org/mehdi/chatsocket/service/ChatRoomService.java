package org.mehdi.chatsocket.service;

import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatRoomId(String senderId,
                                   String recipientId,
                                   boolean createNewRoomIfNotExists);
    String createChat(String senderId,String recipientId);
}
