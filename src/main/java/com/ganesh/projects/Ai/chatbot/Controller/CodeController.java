package com.ganesh.projects.Ai.chatbot.Controller;

import ch.qos.logback.core.pattern.color.RedCompositeConverter;
import com.ganesh.projects.Ai.chatbot.DTO.CodeRequestDTO;
import com.ganesh.projects.Ai.chatbot.entity.CodeRequest;
import com.ganesh.projects.Ai.chatbot.service.CodeRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeController {

    @Autowired
    public CodeRequestService codeRequestService;


    @PostMapping("/createrequest")
    public ResponseEntity<CodeRequest> createRequest(@RequestBody CodeRequestDTO codeRequestDTO) {
        System.out.println("API hit: Received code request");
        return ResponseEntity.ok(codeRequestService.createRequest(codeRequestDTO));
    }




    @GetMapping("/user-requests")
    public ResponseEntity<List<CodeRequest>> getUserRequests(
            @RequestParam("username") String username) {

        List<CodeRequest> requests = codeRequestService.getAllUserRequests(username);
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("/deleterequest/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable("id") Long requestId){
        codeRequestService.deleteRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteuserrequests/{id}")
    public ResponseEntity<Void> deleteUserRequests(@PathVariable("id") Long userId){
        codeRequestService.deleteUserRequests(userId);
        return ResponseEntity.ok().build();
    }


}
