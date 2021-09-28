package com.example.clientui.service;

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

    public String emailTemplate() {
        return "Bonjour," +
                "\n\nLe livre que vous avez réservé est disponible." +
                "\nVous disposez de 48h pour l'emprunter," +
                "\naprès ce délai, votre réservation sera annulé et vous devrez faire une nouvelle demande." +
                "\n\n\nCordialement," +
                "\nvotre Bibliothèque" +
                "\n\n\nCeci est un envoi automatique, merci de ne pas y répondre.";
    }



}
