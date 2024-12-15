package org.mehdi.chatsocket.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMessageDTO {
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String content;
    private Date timestamp;
    private Boolean isRead;
}
