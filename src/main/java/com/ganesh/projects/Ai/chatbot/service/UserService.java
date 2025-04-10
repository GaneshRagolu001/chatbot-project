package com.ganesh.projects.Ai.chatbot.service;

import com.ganesh.projects.Ai.chatbot.DTO.UserDTO;
import com.ganesh.projects.Ai.chatbot.entity.User;
import com.ganesh.projects.Ai.chatbot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public String registerUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCreatedAt(LocalDateTime.now());

        try{
            userRepository.save(user);
        }
        catch (Exception e){
            return "Username already exists";
        }

        return "User registered successfully";
    }

    public User login(UserDTO userDTO) {

        User user = userRepository.findByEmail(userDTO.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found" + userDTO));

        System.out.println(user.getPassword() + "  " + userDTO.getPassword());

        if(!user.getPassword().equals(userDTO.getPassword())){
            throw new RuntimeException("Password not matched");
        }

        user.setPassword(null);
        User client = user;
        return client;
    }


    public void deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found" + username));

        userRepository.delete(user);

    }
}
