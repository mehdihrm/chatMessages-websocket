package org.mehdi.chatsocket.controller;


import lombok.AllArgsConstructor;
import org.mehdi.chatsocket.dtos.MessageResponseDTO;
import org.mehdi.chatsocket.dtos.mapper.DTOMapper;
import org.mehdi.chatsocket.entity.User;
import org.mehdi.chatsocket.exceptions.UserNotFoundException;
import org.mehdi.chatsocket.exceptions.UsernameExistsException;
import org.mehdi.chatsocket.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    private DTOMapper dtoMapper;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try{
            String password = user.getUsername().split("@")[0]+"$chat";
            user.setPassword(passwordEncoder.encode(password));
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(dtoMapper.fromUser(savedUser));
        }catch (UsernameExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponseDTO(e.getMessage()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(e.getMessage()));

        }

    }
    @GetMapping("/get/username")
    public ResponseEntity<?> getUserByEmail(@RequestBody String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(dtoMapper.fromUser(user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(e.getMessage()));
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        try{
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users.stream().map(user-> dtoMapper.fromUser(user)));
        }catch(UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all/online")
    public ResponseEntity<?> getAllUsersOnline(){
        try{
            List<User> users = userService.findConnectedUsers();
            return ResponseEntity.ok(users.stream().map(user-> dtoMapper.fromUser(user)));
        }catch(UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> removeUserByEmail(@PathVariable String id) {
        try {
            userService.removeUserById(id);
            return ResponseEntity.ok(new MessageResponseDTO("User with Id:"+id+" removed successfully."));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(new MessageResponseDTO(e.getMessage()));
        } catch ( Exception e){
            return ResponseEntity.badRequest().body(new MessageResponseDTO(e.getMessage()));
        }
    }

    @PutMapping("/admin/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            return ResponseEntity.ok(dtoMapper.fromUser(userService.updateUser(user)));
        } catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponseDTO(e.getMessage()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponseDTO(e.getMessage()));

        }

    }
}
