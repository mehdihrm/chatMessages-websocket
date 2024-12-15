package org.mehdi.chatsocket.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.mehdi.chatsocket.entity.Status;
import org.mehdi.chatsocket.entity.User;
import org.mehdi.chatsocket.repositories.UserRepository;
import org.mehdi.chatsocket.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
       return this.userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
       return (User) this.userRepository.findByUsername(username);
    }

    @Override
    public User updateUser(User user) {
        User originalUser = userRepository.findById(user.getId()).orElseThrow();
        originalUser.setFullName(user.getFullName());
        originalUser.setUsername(user.getUsername());
        originalUser.setNickName(user.getNickName());
        return userRepository.save(originalUser);
    }

    @Override
    public void disconnect(User user) {
        User originalUser = userRepository.findById(user.getId()).orElseThrow();
        originalUser.setStatus(Status.OFFLINE);
    }

    @Override
    public List<User> findConnectedUsers() {
        return this.userRepository.findAll()
                .stream()
                .filter(user -> user.getStatus() == Status.ONLINE)
                .collect(Collectors.toList());
    }


    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public void removeUserById(String userId) {
        this.userRepository.deleteById(userId);
    }

}
