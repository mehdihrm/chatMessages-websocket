package org.mehdi.chatsocket.service.impl;

import lombok.RequiredArgsConstructor;
import org.mehdi.chatsocket.entity.ChatRoom;
import org.mehdi.chatsocket.repositories.ChatRoomRepository;
import org.mehdi.chatsocket.service.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    @Override
    public Optional<String> getChatRoomId(String senderId,
                                          String recipientId,
                                          boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,recipientId)
                .map(ChatRoom::getChatId)
                .or(()->{
                    if(createNewRoomIfNotExists){
                        String chatId = createChat(senderId,recipientId);
                        return Optional.of(chatId);
                    }
                    return Optional.empty();
                });
    }

    @Override
    public String createChat(String senderId, String recipientId) {
        String chatId = String.format("%s_%s",senderId,recipientId);
        System.out.println(chatId);
        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();
        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();
        System.out.println(senderRecipient);
        System.out.println(recipientSender);
        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);
        return chatId;
    }
}
