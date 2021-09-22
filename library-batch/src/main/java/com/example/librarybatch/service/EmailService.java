package com.example.librarybatch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String userEmail, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@bibliopenclassrooms.com");
        message.setTo(userEmail);
        message.setSubject("notification : votre emprunt est arrivé à expiration. ");
        message.setText(text);
        javaMailSender.send(message);
    }



}
