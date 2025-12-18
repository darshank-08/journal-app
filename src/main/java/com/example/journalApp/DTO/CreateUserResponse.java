package com.example.journalApp.DTO;

import lombok.Data;

@Data
public class CreateUserResponse {

    private String title;
    private String body;
    private String userId;
}
