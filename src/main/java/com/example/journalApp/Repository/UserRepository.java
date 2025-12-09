package com.example.journalApp.Repository;

import com.example.journalApp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUserName(String userName);

    ResponseEntity<?> deleteByUserName(String userName);
}
