package com.example.journalApp.Service;

import com.example.journalApp.DTO.CreateUserReq;
import com.example.journalApp.DTO.CreateUserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserExternalService {

    private final RestTemplate restTemplate;

    public UserExternalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CreateUserResponse create(CreateUserReq req){
        String API_URL = "https://jsonplaceholder.typicode.com/posts";

        return restTemplate.postForObject(
                API_URL,
                req,
                CreateUserResponse.class
        );
    }
}
