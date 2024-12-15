package org.mehdi.chatsocket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mehdi.chatsocket.security.EncryptionUtil;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ChatMessage {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Date timestamp;
    private Boolean isRead;
    public void setContent(String content) {
        try {
            this.content = EncryptionUtil.encrypt(content);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting content", e);
        }
    }

    public String getContent() {
        try {
            return EncryptionUtil.decrypt(this.content);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting content", e);
        }
    }
}
