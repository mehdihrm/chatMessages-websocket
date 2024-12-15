package org.mehdi.chatsocket.service;

import org.mehdi.chatsocket.dtos.ChatMessageDTO;
import org.mehdi.chatsocket.entity.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessageDTO chatMessageDTO);
    List<ChatMessageDTO> findChatMessages(String senderId,String recipientId);
    void markAsRead(String senderId,String recepientId);
}
