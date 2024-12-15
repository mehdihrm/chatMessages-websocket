package org.mehdi.chatsocket.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mehdi.chatsocket.dtos.ChatMessageDTO;
import org.mehdi.chatsocket.dtos.mapper.DTOMapper;
import org.mehdi.chatsocket.entity.ChatMessage;
import org.mehdi.chatsocket.entity.ChatRoom;
import org.mehdi.chatsocket.repositories.ChatMessageRepository;
import org.mehdi.chatsocket.repositories.ChatRoomRepository;
import org.mehdi.chatsocket.service.ChatMessageService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomServiceImpl chatRoomService;
    private final DTOMapper dtoMapper;
    @Override
    public ChatMessage save(ChatMessageDTO chatMessageDTO) {
        var chatId = chatRoomService.getChatRoomId(chatMessageDTO.getSenderId(),chatMessageDTO.getRecipientId(),true)
                .orElseThrow();
        ChatMessage chatMessage = ChatMessage.builder()
                .id(UUID.randomUUID().toString())
                .senderId(chatMessageDTO.getSenderId())
                .recipientId(chatMessageDTO.getRecipientId())
                .chatId(chatId)
                .isRead(false)
                .timestamp(chatMessageDTO.getTimestamp())
                .build();
        chatMessage.setContent(chatMessageDTO.getContent());
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public List<ChatMessageDTO> findChatMessages(String senderId,String recipientId) {
        var chatId = chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                false);
        return (chatId.map(chatMessageRepository::findByChatIdOrderByTimestampAsc).orElse(new ArrayList<>())).stream().map(dtoMapper::fromMessage).collect(Collectors.toList());
    }

    @Override
    public void markAsRead(String senderId, String recepientId) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findBySenderIdAndRecipientId(senderId,recepientId);
        Optional<ChatMessage> lastMessage = chatMessageRepository.findTopByChatIdOrderByTimestampDesc(chatRoom.get().getChatId());
        lastMessage.ifPresent(chatMessage -> {
            if(!chatMessage.getIsRead()){
                if(Objects.equals(chatMessage.getRecipientId(), senderId)){
                    chatMessage.setIsRead(true);

                }
            }
        });

    }
}
