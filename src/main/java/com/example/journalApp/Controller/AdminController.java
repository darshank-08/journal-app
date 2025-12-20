package com.example.journalApp.Controller;

import com.example.journalApp.DTO.MailDTO;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Service.EmailServices;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/Admin")
@RestController
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    EmailServices emailServices;

    @GetMapping("/All-User")
    public ResponseEntity<List<User>> getAllusers(){
       List<User> all = userService.getAll();

       if(all.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/mail")
    public ResponseEntity<?> mail(@RequestBody MailDTO mailReq){
        emailServices.mail(mailReq.getTo(),
                           mailReq.getSubject(),
                           mailReq.getBody());

        return ResponseEntity.ok("Mail sent to " + mailReq.getTo());
    }
}
