package com.example.journalApp.Repository;

import com.example.journalApp.Entity.Journal;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, String> {
}
