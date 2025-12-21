package com.example.journalApp.Scheduler;

import com.example.journalApp.Entity.Journal;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Repository.UserRepositoryImpl;
import com.example.journalApp.Service.EmailServices;
import com.example.journalApp.Service.SentimentAnalysisService;
import com.example.journalApp.apiResponse.catche.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserScheduler {

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    AppCache appCache;

    @Scheduled(cron = "0 9 * * SUN")
    public void findAndMail(){
        List<User> users = userRepository.getUserSA();
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        for (User user: users){
            List<String> last7DaysJournals = new ArrayList<>();

            for (Journal journal : user.getJournals()) {
                if (journal.getCreatedAt() != null &&
                        journal.getCreatedAt().isAfter(sevenDaysAgo)) {

                    last7DaysJournals.add(journal.getContent());
                }
            }

            String entry = String.join(" ", last7DaysJournals);
            String sentiment = sentimentAnalysisService.sentiment(entry);
            emailServices.mail(user.getEmail(), "Last week sentiment", sentiment);
        }

    }

    @Scheduled(cron = "0 0/10 * * * ? *")
    public void cache(){
        appCache.init();
    }
}
