package com.example.librarybatch.writer;

import com.example.librarybatch.config.BatchConfig;
import com.example.librarybatch.model.LoanBean;
import com.example.librarybatch.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanExpiredItemWriter implements ItemWriter<LoanBean> {

    private Logger logger = LoggerFactory.getLogger(LoanExpiredItemWriter.class);


    @Autowired
    EmailService emailService;

    @Override
    public void write(List<? extends LoanBean> loans) throws Exception {

            for (LoanBean loanExpired : loans) {
                logger.info("item writer : " + loanExpired.getUserEmail());
                emailService.sendSimpleMessage(loanExpired.getUserEmail(), emailTemplate());
            }

    }

    public String emailTemplate() {
        String text = "Bonjour," +
                "\n\nLa date de retour pour votre emprunt est dépassé." +
                "\nVous avez la possibilité, si vous ne l'avez pas encore fait, de prolonger la durée de votre emprunt de 4 semaines." +
                "\nSinon, pensez à ramener le livre à votre bibliothèque au plus vite pour éviter une pénalité." +
                "\n\n\nVotre Bibliothèque" +
                "\n\n\nCeci est un envoi automatique, merci de ne pas y répondre.";
        return text;
    }
}
