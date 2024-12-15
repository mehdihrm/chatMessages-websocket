package org.mehdi.chatsocket.service;

import org.mehdi.chatsocket.entity.User;

import java.util.List;

public interface UserService {
     User saveUser(User user);
     User getUserByUsername(String username);
     User updateUser(User user);
     void disconnect(User user);
     List<User> findConnectedUsers();
     List<User> getAllUsers();
     void removeUserById(String userId);
}
