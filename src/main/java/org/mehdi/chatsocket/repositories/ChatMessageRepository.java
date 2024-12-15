package org.mehdi.chatsocket.repositories;

import org.mehdi.chatsocket.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findByChatIdOrderByTimestampAsc(String s);

    Optional<ChatMessage> findTopByChatIdOrderByTimestampDesc(String chatId);
}
