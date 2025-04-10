package com.ganesh.projects.Ai.chatbot.Controller;

import com.ganesh.projects.Ai.chatbot.DTO.UserDTO;
import com.ganesh.projects.Ai.chatbot.entity.User;
import com.ganesh.projects.Ai.chatbot.repository.UserRepository;
import com.ganesh.projects.Ai.chatbot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO){

        return ResponseEntity.ok(userService.login(userDTO));
    }

    @DeleteMapping("/deleteuser/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }


}
