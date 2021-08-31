package com.example.librarybatchnotif.processor;

import com.example.librarybatchnotif.model.AccountBean;
import com.example.librarybatchnotif.model.LoanBean;
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


    @Override
    @Cacheable("booking")
    public BookingBean process(BookingBean bookingBean) throws Exception {

        System.out.println("item user processor");
        String email = (userProxy.selectAccount(bookingBean.getUser())).getEmail();

        bookingBean.setUserEmail(email);
        System.out.println(bookingBean.getUserEmail());

        bookingProxy.deleteBookingExpired(bookingBean.getId());

        return bookingBean;
    }


}
