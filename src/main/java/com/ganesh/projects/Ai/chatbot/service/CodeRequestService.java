package com.ganesh.projects.Ai.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.projects.Ai.chatbot.DTO.CodeRequestDTO;
import com.ganesh.projects.Ai.chatbot.entity.CodeRequest;
import com.ganesh.projects.Ai.chatbot.entity.User;
import com.ganesh.projects.Ai.chatbot.repository.CodeRequestRepository;
import com.ganesh.projects.Ai.chatbot.repository.UserRepository;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
public class CodeRequestService {

    @Autowired
    public CodeRequestRepository codeRequestRepository;

    @Autowired
    public UserRepository userRepository;


    @Autowired
    public ChatGptService chatGptService;


    public CodeRequest createRequest(CodeRequestDTO codeRequestDTO) {

        User user = userRepository.findByUsername(codeRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with name " + codeRequestDTO.getUsername()));





        CodeRequest codeRequest = new CodeRequest();
        codeRequest.setUser(user);
        codeRequest.setInputCode(codeRequestDTO.getInputCode());
        codeRequest.setCreatedAt(LocalDateTime.now());

        codeRequestRepository.save(codeRequest);

        String improvedCode = null;
        try {
            improvedCode = chatGptService.getImprovedCode(codeRequest.getId());


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(improvedCode);
            String mainResponseText = rootNode
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text")
                    .asText();

            // Save full response in DB (optional: keep full JSON for history)
            codeRequest.setGptImprovedCode(mainResponseText);
            codeRequestRepository.save(codeRequest);

            return codeRequest;

        } catch (Exception e) {
            throw new RuntimeException("Failed to process GPT response", e);
        }
    }


    public List<CodeRequest> getAllUserRequests(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("user not found by id " + username));
        Long userId = user.getId();
        List<CodeRequest> requests;
        if(userId != null){
            requests = codeRequestRepository.findAllByUserId(userId);
        } else {
            throw new IllegalArgumentException("Either userId or username must be provided");
        }

        return requests;

    }


    public void deleteRequest(Long requestId) {
        CodeRequest codeRequest = codeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("request not found by id " + requestId));

        codeRequestRepository.delete(codeRequest);

    }

    public void deleteUserRequests(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found by id " + userId));

        codeRequestRepository.deleteAllByUserId(userId);
    }
}
