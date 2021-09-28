package com.example.librarybatchnotif.service;

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
        message.setFrom("noreply@openclassroomsbiblio.com");
        message.setTo(userEmail);
        message.setSubject("notification : votre livre est disponible. ");
        message.setText(text);
        javaMailSender.send(message);
    }



}
