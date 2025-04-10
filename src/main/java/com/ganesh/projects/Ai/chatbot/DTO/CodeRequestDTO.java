package com.ganesh.projects.Ai.chatbot.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeRequestDTO {
    private Long id;
    private String username;
    private String inputCode;
    private String gptImprovedCode;
    private LocalDateTime createdAt;
}