package com.example.journalApp.Controller;

import com.example.journalApp.DTO.CatFactDTO;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Service.CatFactService;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    CatFactService catFactService;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return userService.registerUsers(user);
    }

    @GetMapping("/cat-facts/")
    public CatFactDTO catFacts(@PathVariable Integer length) {
        return catFactService.getRandomFact();
    }

}
