package com.ganesh.projects.Ai.chatbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganesh.projects.Ai.chatbot.entity.CodeRequest;
import com.ganesh.projects.Ai.chatbot.repository.CodeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public CodeRequestRepository codeRequestRepository;

    // Update the URL to the Gemini API endpoint
    private final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    @Value("${openai.api.key}")
    private String GEMINI_API_KEY; // Replace with your actual Gemini API key

    public String getImprovedCode(Long requestId) throws JsonProcessingException {
        CodeRequest codeRequest = codeRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("code request not found"));
        System.out.println(codeRequest.getInputCode());

        String prompt = "You are a programming-focused AI assistant. You only respond to valid code or programming-related queries. " +
                "If the input is not related to code, programming, or software development, respond with this exact message: " +
                "'This model is designed only for programming-related queries, so I can't respond to your request.'\n\n" +
                "Input:\n" + codeRequest.getInputCode() +
                "\n\nIf this input is valid code, improve it and provide a list of changes made.";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();

        // Create the request body according to the Gemini API structure
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("contents", new Object[]{
                new HashMap<String, Object>() {{
                    put("parts", new Object[]{
                            new HashMap<String, String>() {{
                                put("text", prompt);
                            }}
                    });
                }}
        });

        String requestBody = objectMapper.writeValueAsString(requestMap);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make the request to the Gemini API
        ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL + GEMINI_API_KEY, HttpMethod.POST, entity, String.class);

        String improvedCode = response.getBody();



        codeRequest.setGptImprovedCode(improvedCode);
        codeRequestRepository.save(codeRequest);

        return improvedCode;
    }

}