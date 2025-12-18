package com.example.journalApp.Service;


import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<User> registerUsers(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("user"));
            User saved = userRepository.save(user);
            log.info("HEHEHEHEHE !!!");
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("HAHAHAHA");
            log.debug("HEHEHEHEHE !!!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<User> createUser(User user) {
        User saved = userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }


    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public ResponseEntity<?> delete(String userName){
        try{
            userRepository.deleteByUserName(userName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public Optional<User> findByID(String id) {
        return  userRepository.findById(id);
    }
}
