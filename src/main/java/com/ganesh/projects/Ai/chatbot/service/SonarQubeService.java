//package com.ganesh.projects.Ai.chatbot.service;
//
//import com.ganesh.projects.Ai.chatbot.entity.CodeRequest;
//import com.ganesh.projects.Ai.chatbot.entity.User;
//import com.ganesh.projects.Ai.chatbot.repository.CodeRequestRepository;
//import com.ganesh.projects.Ai.chatbot.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class SonarQubeService {
//
//
//   private final RestTemplate restTemplate = new RestTemplate();
//
//   @Autowired
//   public CodeRequestRepository codeRequestRepository;
//
//   @Autowired
//   public UserRepository userRepository;
//
//    private final String SONARQUBE_URL = "http://localhost:9000/api/issues/search";
//    private final String SONARQUBE_TOKEN = "sqa_df1e1814335f0fa9bd3159963191b42b09ae0819";
//
//    public String analzeCode(Long userId,String projectKey,String code){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found with " + userId));
//
//        String url = SONARQUBE_URL + "?componentKeys=" + projectKey;
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBasicAuth(SONARQUBE_TOKEN,"");
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
//        String sonarIssues = response.getBody();
//
//        CodeRequest codeRequest = new CodeRequest();
//        codeRequest.setUser(user);
//        codeRequest.setProjectKey(projectKey);
//        codeRequest.setInputCode(code);
//        codeRequest.setSonarqubeAnalysis(sonarIssues);
//
//        codeRequestRepository.save(codeRequest);
//
//        return sonarIssues;
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
