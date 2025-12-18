package com.example.journalApp.Repository;

import com.example.journalApp.Entity.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepositoryImpl {

    private final MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<User> getUser(String userName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName));

        try {
            List<User> users = mongoTemplate.find(query, User.class);
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUserSA() {
        Query query = new Query();
        query.addCriteria(
                Criteria.where("email")
                        .regex("^[a-zA-Z0-9._%+-]+@gmail\\.com$")
        );
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

        try {
            List<User> users = mongoTemplate.find(query, User.class);
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

