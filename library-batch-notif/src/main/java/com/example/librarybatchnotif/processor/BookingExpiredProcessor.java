package com.example.librarybatchnotif.processor;

import com.example.librarybatchnotif.model.BookBean;
import com.example.librarybatchnotif.proxy.BookingProxy;
import com.example.librarybatchnotif.proxy.UserProxy;
import com.example.librarybatchnotif.model.BookingBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingExpiredProcessor implements ItemProcessor<BookingBean, BookingBean> {

    @Autowired
    UserProxy userProxy;

    @Autowired
    BookingProxy bookingProxy;


    @Override
    @Cacheable("booking")
    public BookingBean process(BookingBean bookingBean) throws Exception {

        //récupérer l'id du livre qui est concerné par la réservation au délai dépassé
        BookBean book = new BookBean();
        book.setId(bookingBean.getBook());
        System.out.println("book : " + book.getId());

        //supprimer la réservation au délai dépassé
        bookingProxy.deleteBookingExpired(bookingBean.getId());

        //trouver la réservation suivante grace à l'id du livre
        List<BookingBean> bookings = bookingProxy.listBookingByBookOrderByStartDate(book.getId());
        BookingBean nextBooking = null;

        if (!bookings.isEmpty()){
            nextBooking = bookings.get(0);
            System.out.println("nextBooking :" + nextBooking);

            //récupérer le mail de la personne qui a fait cette réservation
            String email = (userProxy.selectAccount(nextBooking.getUser())).getEmail();
            nextBooking.setUserEmail(email);
            System.out.println("email : " + email);

        }

        return nextBooking;
    }


}
