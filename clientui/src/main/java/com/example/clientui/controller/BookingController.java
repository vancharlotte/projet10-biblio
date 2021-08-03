package com.example.clientui.controller;

import com.example.clientui.beans.*;
import com.example.clientui.client.LibraryAccountClient;
import com.example.clientui.client.LibraryBookClient;
import com.example.clientui.client.LibraryBookingClient;
import com.example.clientui.client.LibraryLoanClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class BookingController {

    private Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private LibraryBookingClient bookingClient;

    @Autowired
    private LibraryLoanClient loanClient;

    @Autowired
    private LibraryBookClient bookClient;

    @Autowired
    private LibraryAccountClient accountClient;


    @GetMapping("/bookings")
    public String ListLoans(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        AccountBean user = accountClient.findUsername(username);

        List<BookingBean> bookingsByUser = bookingClient.listBookingByUser(user.getId());
        LinkedHashMap<BookingBean, BookBean> map = new LinkedHashMap<>();

        for (int i = 0; i < bookingsByUser.size(); i++) {
            BookBean book = bookClient.displayBook(bookingsByUser.get(i).getBook());
            map.put(bookingsByUser.get(i), book);

            List<BookingBean> bookingsByBook = bookingClient.listBookingByBook(book.getId());
            bookingsByBook.get(0);

        }

        model.addAttribute("bookingsByUser", bookingsByUser);
        model.addAttribute("map", map);



        return "ListBookings";
    }

    //recup start la + proche
    //recup nb au classement
}
