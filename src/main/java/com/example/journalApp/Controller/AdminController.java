package com.example.journalApp.Controller;

import com.example.journalApp.Entity.User;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("Admin")
@RestController
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/All-User")
    public ResponseEntity<List<User>> getAllusers(){
       List<User> all = userService.getAll();

       if(all.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(all, HttpStatus.OK);
    }
}
