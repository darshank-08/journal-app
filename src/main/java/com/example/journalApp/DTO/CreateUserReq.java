package com.example.journalApp.DTO;

import lombok.Data;

@Data
public class CreateUserReq {

    private String title;
    private String body;
    private String userId;
}
