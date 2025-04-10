package com.ganesh.projects.Ai.chatbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "code_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String inputCode;

    @Column(columnDefinition = "TEXT")
    private String gptImprovedCode;

    private LocalDateTime createdAt;
}
