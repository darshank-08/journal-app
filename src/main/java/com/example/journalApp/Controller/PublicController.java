package com.example.journalApp.Controller;

import com.example.journalApp.DTO.CatFactDTO;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.UserRepositoryImpl;
import com.example.journalApp.Scheduler.UserScheduler;
import com.example.journalApp.Service.CatFactService;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    CatFactService catFactService;

    @Autowired
    UserRepositoryImpl userRepositoryImpl;

    @Autowired
    UserScheduler userScheduler;

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return userService.registerUsers(user);
    }

    @GetMapping("/cat-facts/")
    public CatFactDTO catFacts(@PathVariable Integer length) {
        return catFactService.getRandomFact();
    }

    @GetMapping("/users/{userName}")
    public ResponseEntity<?> list(@PathVariable String userName) {

        List<User> users = userRepositoryImpl.getUser(userName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users-with-sentiments")
    public ResponseEntity<?> list() {

        List<User> users = userRepositoryImpl.getUserSA();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/sentiment-analysis")
    public ResponseEntity mailSend(){
        return ResponseEntity.ok("mail sent to:" + userScheduler.findAndMail());
    }

}
