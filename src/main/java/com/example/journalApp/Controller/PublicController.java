package com.example.journalApp.Controller;

import com.example.journalApp.DTO.CatFactDTO;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.UserRepositoryImpl;
import com.example.journalApp.Scheduler.UserScheduler;
import com.example.journalApp.Service.CatFactService;
import com.example.journalApp.Service.UserDetailServicesIMPL;
import com.example.journalApp.Service.UserService;
import com.example.journalApp.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private CatFactService catFactService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private UserScheduler userScheduler;

    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private UserDetailServicesIMPL userDetailServicesIMPL;

    @Autowired
    private  JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        return userService.registerUsers(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        try {
            auth.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUserName(), user.getPassword())
            );
            UserDetails userDetails = userDetailServicesIMPL.loadUserByUsername(user.getUserName());
            String jwt = jwtUtils.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        }catch (Exception e){
           log.error("Error while creating token");
           return ResponseEntity.badRequest().body("Incorrect username or password");
        }

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
