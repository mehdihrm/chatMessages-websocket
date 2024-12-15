package org.mehdi.chatsocket.service;

import org.mehdi.chatsocket.dtos.AuthenticationDTO;
import org.mehdi.chatsocket.dtos.UserRegisterRequestDTO;
import org.mehdi.chatsocket.entity.User;

public interface AuthenticationService {
    AuthenticationDTO register(UserRegisterRequestDTO request);
    AuthenticationDTO authenticate(User request);
}
