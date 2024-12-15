package org.mehdi.chatsocket.controller;

import lombok.RequiredArgsConstructor;
import org.mehdi.chatsocket.dtos.ChatMessageDTO;
import org.mehdi.chatsocket.dtos.MessageResponseDTO;
import org.mehdi.chatsocket.entity.ChatMessage;
import org.mehdi.chatsocket.entity.ChatNotification;
import org.mehdi.chatsocket.service.ChatMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(
            @Payload ChatMessageDTO chatMessage
    ){
        chatMessage.setId(UUID.randomUUID().toString());
        chatMessage.setTimestamp(new Date());
        ChatMessage savedMsg = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(savedMsg.getRecipientId(),"/queue/messages",
                ChatNotification.builder()
                        .id(savedMsg.getId())
                        .senderId(savedMsg.getSenderId())
                        .recipientId(savedMsg.getRecipientId())
                        .content(savedMsg.getContent())
                        .timestamp(savedMsg.getTimestamp().toString())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessageDTO>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ){
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId,recipientId));
    }
    @GetMapping("/messages/marklastasread/{senderId}/{recipientId}")
    public ResponseEntity<?> markMessageAsRead(
            @PathVariable("senderId") String senderId,
            @PathVariable("recipientId") String recipientId
    ){
        try{
            chatMessageService.markAsRead(senderId,recipientId);
            return ResponseEntity.ok(new MessageResponseDTO("Marked as read"));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponseDTO("Error : ")+e.getMessage());
        }
    }
}
