package com.example.journalApp.Controller;

import com.example.journalApp.Entity.Journal;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Service.JournalService;
import com.example.journalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/journal")
@RestController
public class JournalController {

    @Autowired
    JournalService journalService;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createEntry(@RequestBody Journal journal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return journalService.create(journal, userName);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> jrn = user.getJournals();

        if (jrn.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(jrn, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getByIDJournalEntriesOfUser(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        Optional<Journal> journal = user.getJournals()
                .stream()
                .filter(j -> j.getId().equals(id))
                .findFirst();

        if (journal.isPresent()){
            return new ResponseEntity<>(journal, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable String id,
                                         @RequestBody Journal journal)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        return journalService.update(id, journal, userName);
    }

    @DeleteMapping("/delete/{myID}")
    public ResponseEntity<?> deleteByID(@PathVariable String myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return journalService.delete(myID, userName);
    }
}
