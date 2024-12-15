package org.mehdi.chatsocket.dtos.mapper;

import lombok.AllArgsConstructor;
import org.mehdi.chatsocket.dtos.ChatMessageDTO;
import org.mehdi.chatsocket.dtos.UserDTO;
import org.mehdi.chatsocket.entity.ChatMessage;
import org.mehdi.chatsocket.entity.User;
import org.mehdi.chatsocket.exceptions.UserNotFoundException;
import org.mehdi.chatsocket.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DTOMapper {
    private UserRepository userRepository;

    public UserDTO fromUser(User user){
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        return userDTO;
    }
    public User fromUserDTO(UserDTO userDTO){
        return userRepository.findById(userDTO.getId()).orElseThrow(() -> new UserNotFoundException("User does not exist."));
    }
    public ChatMessageDTO fromMessage(ChatMessage msg){
        ChatMessageDTO msgDTO = new ChatMessageDTO();
        BeanUtils.copyProperties(msg,msgDTO);
        msgDTO.setContent(msg.getContent());
        return msgDTO;
    }
}
