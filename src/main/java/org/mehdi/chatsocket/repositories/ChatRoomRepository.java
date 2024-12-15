package org.mehdi.chatsocket.repositories;

import org.mehdi.chatsocket.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
    List<Optional<ChatRoom>> findBySenderId(String senderId);
}
