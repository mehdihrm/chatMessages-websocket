package org.mehdi.chatsocket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id ;
    private String fullName;
    private String username;
    private boolean enabled;
    private boolean notified;
}
