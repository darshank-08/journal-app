package com.example.journalApp.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Journal_App_Config")
@Data
@NoArgsConstructor
public class AppCacheConfig {

    private String key;
    private String value;

}
