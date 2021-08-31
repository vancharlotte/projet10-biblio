package com.example.librarybatchnotif.writer;

import com.example.librarybatchnotif.service.EmailService;
import com.example.librarybatchnotif.model.BookingBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingExpiredItemWriter implements ItemWriter<BookingBean> {

    @Autowired
    EmailService emailService;

    @Override
    public void write(List<? extends BookingBean> bookings) throws Exception {
            for (BookingBean bookingExpired : bookings) {
                System.out.println("item writer : " + bookingExpired.getUserEmail());
                emailService.sendSimpleMessage(bookingExpired.getUserEmail(), emailTemplate());
            }

    }

    public String emailTemplate() {
        String text = "Bonjour," +
                "\n\n Le livre que vous avez réservé est disponible." +
                "\nVous avez 48h pour vous rendre dans votre bibliothèque et l'emprunder." +
                "\nDépassé ce délai, votre réservation sera annulé." +
                "\n\n\nCordialement." +
                "\n\nVotre Bibliothèque" +
                "\n\n\nCeci est un envoi automatique, merci de ne pas y répondre.";
        return text;
    }
}
