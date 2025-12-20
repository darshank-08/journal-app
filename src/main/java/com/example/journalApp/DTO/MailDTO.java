package com.example.journalApp.DTO;

import lombok.Data;

@Data
public class MailDTO {

    private String from;
    private String to;
    private String subject;
    private String body;
}
