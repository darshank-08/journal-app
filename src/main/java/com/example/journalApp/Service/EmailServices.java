package com.example.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServices {

    @Autowired
    private JavaMailSender javaMailSender;


    @Value("${spring.mail.from}")
    private String from;

    public void mail (String to, String subject, String body){
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setFrom(from);
            mail.setSubject(subject);
            mail.setText(body);

            javaMailSender.send(mail);
        } catch (Exception e) {
            log.error("Exception while sending mail!!" + e);
        }
    }
}
