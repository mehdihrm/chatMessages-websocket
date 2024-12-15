package org.mehdi.chatsocket.controller;

import lombok.AllArgsConstructor;
import org.mehdi.chatsocket.dtos.AuthenticationDTO;
import org.mehdi.chatsocket.dtos.MessageResponseDTO;
import org.mehdi.chatsocket.dtos.UserRegisterRequestDTO;
import org.mehdi.chatsocket.entity.User;
import org.mehdi.chatsocket.repositories.UserRepository;
import org.mehdi.chatsocket.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO request){
        try{
            if(userRepository.existsByUsername(request.getUsername())){
                return ResponseEntity.badRequest().body(new MessageResponseDTO("Error: Username is already taken!"));
            }
            return ResponseEntity.ok(authenticationService.register(request));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User request){
        try{
            AuthenticationDTO authenticationResponse = authenticationService.authenticate(request);
            User user = (User) userRepository.findByUsername(authenticationResponse.getUsername());
                return ResponseEntity.ok().body(Map.of("token",authenticationResponse.getToken()));
        }catch (BadCredentialsException e){
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }


    }
}
