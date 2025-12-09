package com.example.journalApp.Repository;

import com.example.journalApp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository<User, String> {
}
