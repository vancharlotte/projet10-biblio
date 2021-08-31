package com.example.librarybatchnotif.processor;

import com.example.librarybatchnotif.model.AccountBean;
import com.example.librarybatchnotif.model.BookBean;
import com.example.librarybatchnotif.model.LoanBean;
import com.example.librarybatchnotif.proxy.BookProxy;
import com.example.librarybatchnotif.proxy.BookingProxy;
import com.example.librarybatchnotif.proxy.UserProxy;
import com.example.librarybatchnotif.model.BookingBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BookingExpiredProcessor implements ItemProcessor<BookingBean, BookingBean> {

    @Autowired
    UserProxy userProxy;

    @Autowired
    BookingProxy bookingProxy;

    @Autowired
    BookProxy bookProxy;


    @Override
    @Cacheable("booking")
    public BookingBean process(BookingBean bookingBean) throws Exception {

        System.out.println("item processor ");

        //récupérer l'id du livre qui est concerné par la réservation au délai dépassé
        BookBean book = new BookBean();
        book.setId(bookingBean.getBook());

        //supprimer la réservation au délai dépassé
        bookingProxy.deleteBookingExpired(bookingBean.getId());

        //trouver la réservation suivante grace à l'id du livre
        BookingBean nextBooking = bookingProxy.listBookingByBookOrderByStartDate(book.getId()).get(0);

        //récupérer le mail de la personne qui a fait cette réservation
        String email = (userProxy.selectAccount(nextBooking.getUser())).getEmail();
        nextBooking.setUserEmail(email);
        System.out.println(bookingBean.getUserEmail());

        return nextBooking;
    }


}
