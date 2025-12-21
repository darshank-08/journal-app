package com.example.journalApp.Entity;

import com.example.journalApp.Enum.Sentiments;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("journalEntry")
@Data
@NoArgsConstructor
public class Journal {

    @Id
    private String id;

    private String title;
    private String content;

    private LocalDateTime createdAt;

    private Sentiments sentiments;

}
