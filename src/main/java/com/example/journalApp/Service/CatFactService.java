package com.example.journalApp.Service;

import com.example.journalApp.DTO.CatFactDTO;
import com.example.journalApp.apiResponse.catche.AppCache;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class CatFactService {

    private final RestTemplate restTemplate;
    private final AppCache appCache;

    public CatFactService(RestTemplate restTemplate, AppCache appCache) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
    }

    public CatFactDTO getRandomFact() {

        // DB cache se API URL uthao
        // String apiUrl = appCache.get("api");
          String apiUrl = "https://catfact.ninja/fact";

        try {
            return restTemplate.getForObject(apiUrl, CatFactDTO.class);
        } catch (RestClientException e) {
            throw new RuntimeException("External API failed !!! " + e.getMessage());
        }
    }
}

//    agr hume yml se leni hai hamaari api key
//    @Value("${api_key}")
//    private String apikey;


