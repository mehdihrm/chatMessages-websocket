package org.mehdi.chatsocket.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.mehdi.chatsocket.dtos.AuthenticationDTO;
import org.mehdi.chatsocket.dtos.UserRegisterRequestDTO;
import org.mehdi.chatsocket.entity.User;
import org.mehdi.chatsocket.repositories.UserRepository;
import org.mehdi.chatsocket.service.AuthenticationService;
import org.mehdi.chatsocket.service.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationDTO register(UserRegisterRequestDTO request) {
        System.out.println("UserRegister"+request.toString());
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        return new AuthenticationDTO(user.getUsername(),token);
    }

    @Override
    public AuthenticationDTO authenticate(User request) {
        User user = (User) userRepository.findByUsername(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Nom d'utilisateur ou mot de passe incorrect");
        }
        String token = jwtService.generateToken(user);
        return new AuthenticationDTO(user.getUsername(), token);
    }
}
