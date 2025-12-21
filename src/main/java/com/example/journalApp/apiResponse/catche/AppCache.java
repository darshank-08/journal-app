package com.example.journalApp.apiResponse.catche;

import com.example.journalApp.Entity.AppCacheConfig;
import com.example.journalApp.Repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigRepository configRepository;

    private Map<String, String> cache = new HashMap<>();


    @PostConstruct
    public void init() {

        List<AppCacheConfig> allConfigs = configRepository.findAll();

        for (AppCacheConfig config : allConfigs) {
            cache.put(config.getKey(), config.getValue());
        }

    }

    // getter method
    public String get(String key) {
        return cache.get(key);
    }
}
