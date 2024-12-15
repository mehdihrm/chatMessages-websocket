package org.mehdi.chatsocket.dtos;

import lombok.Data;

@Data
public class UserRegisterRequestDTO {
    String fullName;
    String nickname;
    String username;
    String password;
}
