package com.example.journalApp.Service;

import com.example.journalApp.Entity.Journal;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.JournalRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalService {

    @Autowired
    JournalRepository journalRepository;
    @Autowired
    UserService userService;


    @Transactional
    public ResponseEntity<?> create(Journal journal, String userName) {

        User user = userService.findByUserName(userName);
        try {
            Journal saved = journalRepository.save(journal);
            user.getJournals().add(saved);
            userService.createUser(user);
            return ResponseEntity.status(201).body(saved); // 201 Created
        } catch (Exception e) {
            log.error("e: ", e);
            return ResponseEntity.status(500).body("Something went wrong: " + e.getMessage());
        }
    }

    public List<Journal> getAll() {
        List<Journal> journals = journalRepository.findAll();
        return journals;
    }

    public Optional<Journal> fetchByID(String myID) {
        return journalRepository.findById(myID);
    }


    public ResponseEntity<Journal> update(String id,
                                          Journal journal,
                                          String userName) {
        User user = userService.findByUserName(userName);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Journal> existingopt = user.getJournals().stream().filter(x -> x.getId().equals(id)).findFirst();

        if (existingopt.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Journal existing = existingopt.get();
        existing.setTitle(journal.getTitle());
        existing.setContent(journal.getContent());

        journalRepository.save(existing);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> delete(String myID, String userName){
        User user = userService.findByUserName(userName);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Journal> existingopt = user.getJournals().stream().filter(x -> x.getId().equals(myID)).findFirst();

        journalRepository.deleteById(myID);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
