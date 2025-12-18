package com.example.journalApp.Controller;

import com.example.journalApp.DTO.CreateUserReq;
import com.example.journalApp.DTO.CreateUserResponse;
import com.example.journalApp.Service.UserExternalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Third-Party-Registration")
public class ExternalUserController {

    private final UserExternalService userExternalService;

    public ExternalUserController(UserExternalService userExternalService) {
        this.userExternalService = userExternalService;
    }

    @PostMapping("/create")
    public CreateUserResponse create (@RequestBody CreateUserReq req){
        return userExternalService.create(req);
    }
}

