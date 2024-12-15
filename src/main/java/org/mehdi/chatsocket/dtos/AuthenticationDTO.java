package org.mehdi.chatsocket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class AuthenticationDTO {
    private String username;
    private String token;

}
