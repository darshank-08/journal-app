package com.example.journalApp.Controller;

import com.example.journalApp.Entity.Journal;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEntry(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldUser = userService.findByUserName(userName);

        if(oldUser != null){
            oldUser.setUserName(user.getUserName());
            oldUser.setPassword(user.getPassword());
        }

        userService.registerUsers(oldUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Name = authentication.getName();

        userService.delete(Name);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
