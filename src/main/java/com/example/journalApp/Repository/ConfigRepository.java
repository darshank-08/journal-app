package com.example.journalApp.Repository;


import com.example.journalApp.Entity.AppCacheConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<AppCacheConfig, String> {
}
