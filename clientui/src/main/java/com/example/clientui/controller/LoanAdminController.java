package com.example.clientui.controller;

import com.example.clientui.EmailService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class LoanAdminController {

    private Logger logger = LoggerFactory.getLogger(LoanController.class);

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
    @GetMapping(value = "/loan/return/{id}")
    public String returnLoan(@PathVariable int id) {
        LoanBean loan = loanClient.selectLoan(id);

        int bookId = bookClient.selectCopy(loan.getCopy()).getBook();

        List<BookingBean> bookings = bookingClient.listBookingByBookOrderByStartDate(bookId);
        AccountBean user = accountClient.selectAccount(bookings.get(0).getUser());

        emailService.sendSimpleMessage(user.getEmail(), emailService.emailTemplate());


        loanClient.returnLoan(id);
        logger.info("return loan");
        return "redirect:/loans";

    }


    // return by admin only
    @GetMapping(value = "/loan/add/{loan}")
    public String addLoan(@PathVariable LoanBean loan) {

        //delete booking by user when create a loan
        if (bookingClient.existByUserAndBook(loan.getUser(), bookClient.selectCopy(loan.getCopy()).getBook())) {
            BookingBean booking = bookingClient.findByUserAndBook(loan.getUser(), bookClient.selectCopy(loan.getCopy()).getBook());
            bookingClient.deleteBooking(booking.getId());
        }

        loanClient.addLoan(loan);
        logger.info("new loan");

        return "redirect:/loans";

    }
}
