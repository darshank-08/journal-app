package com.example.journalApp.Scheduler;

import com.example.journalApp.Entity.Journal;
import com.example.journalApp.Entity.User;
import com.example.journalApp.Enum.Sentiments;
import com.example.journalApp.Repository.UserRepositoryImpl;
import com.example.journalApp.Service.EmailServices;
import com.example.journalApp.apiResponse.catche.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private EmailServices emailServices;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    AppCache appCache;

//    @Scheduled(cron = "0 0 9 * * SUN")
    public List<String> findAndMail(){

        List<User> users = userRepository.getUserSA();
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        for (User user: users){

            Map<Sentiments, Integer> sentimentCount = new HashMap<>();

            for (Journal journal : user.getJournals()) {
                if (journal.getCreatedAt() != null &&
                        journal.getCreatedAt().isAfter(sevenDaysAgo)) {

                    Sentiments sentiments = journal.getSentiments();

                    // 🔥 COUNTING LOGIC
                    sentimentCount.put(
                            sentiments,
                            sentimentCount.getOrDefault(sentiments, 0) + 1
                    );
                }
            }

            Sentiments mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiments, Integer> entry : sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null){
                String body = user.getUserName() + ", " + mostFrequentSentiment.toString();
                emailServices.mail(user.getEmail(), "Sentiment for last week!!", body);
            }
        }

        return users.stream()
                .map(User::getUserName)
                .toList();


    }

    @Scheduled(cron = "0 */10 * * * *")
    public void cache(){
        appCache.init();
    }
}
