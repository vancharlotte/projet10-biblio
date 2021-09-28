package com.example.clientui.controller;

import com.example.clientui.service.EmailService;
import com.example.clientui.beans.AccountBean;
import com.example.clientui.beans.BookingBean;
import com.example.clientui.beans.LoanBean;
import com.example.clientui.client.LibraryAccountClient;
import com.example.clientui.client.LibraryBookClient;
import com.example.clientui.client.LibraryBookingClient;
import com.example.clientui.client.LibraryLoanClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class LoanAdminController {

    private Logger logger = LoggerFactory.getLogger(LoanAdminController.class);

    @Autowired
    private LibraryLoanClient loanClient;

    @Autowired
    private LibraryBookingClient bookingClient;

    @Autowired
    private LibraryBookClient bookClient;

    @Autowired
    private LibraryAccountClient accountClient;

    @Autowired
    private EmailService emailService;


    // return by admin only
    @GetMapping(value = "/loan/return/{loanId}")
    public String returnLoan(@PathVariable int loanId) {
        LoanBean loan = loanClient.selectLoan(loanId);
        int bookId = bookClient.selectCopy(loan.getCopy()).getBook();

        List<BookingBean> bookings = bookingClient.listBookingByBookOrderByStartDate(bookId);

        if(!bookings.isEmpty()){

            AccountBean user = accountClient.selectAccount(bookings.get(0).getUser());
            bookingClient.notifBooking(bookings.get(0));

            emailService.sendSimpleMessage(user.getEmail(), emailService.emailTemplate());
        }

        loanClient.returnLoan(loanId);
        return "redirect:/";

    }



    // add by admin only
    @GetMapping(value = "/loan/add")
    public String addLoan() {
        LoanBean loan = new LoanBean();
        loan.setUser(4);
        loan.setCopy(7);
        loan.setStartDate(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 28);
        loan.setEndDate(calendar.getTime());

        //delete booking by user when create a loan
        if (bookingClient.existByUserAndBook(loan.getUser(), bookClient.selectCopy(loan.getCopy()).getBook())) {
            BookingBean booking = bookingClient.findByUserAndBook(loan.getUser(), bookClient.selectCopy(loan.getCopy()).getBook());
            bookingClient.deleteBooking(booking.getId());
        }

        logger.info(loan.toString());
        loanClient.addLoan(loan);
        logger.info("new loan");

        return "redirect:/";

    }
}
