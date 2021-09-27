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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
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
    public String ListBookings(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        AccountBean user = accountClient.findUsername(username);

        List<BookingBean> bookingsByUser = bookingClient.listBookingByUser(user.getId());
        List<BookingInformation> listBookingsInfo = new ArrayList<>();

        for (int i = 0; i < bookingsByUser.size(); i++) {

            BookingInformation bookingInfo = new BookingInformation();

            bookingInfo.setBooking(bookingsByUser.get(i).getId());

            BookBean book = bookClient.displayBook(bookingsByUser.get(i).getBook());

            int rank = 0;
            List<BookingBean> bookingByBook = bookingClient.listBookingByBookOrderByStartDate(book.getId());
            for (int l = 0; l < bookingByBook.size(); l++) {
                if (bookingByBook.get(l).getUser() == user.getId()) {
                    rank = l + 1;
                }
            }

            List<CopyBean> copiesByBook = bookClient.listCopies(book.getId());
            List<LoanBean> loansByBook = new ArrayList<>();


            for (int j = 0; j < copiesByBook.size(); j++) {
                LoanBean loan = loanClient.getLoanByCopyAndReturnedNot(copiesByBook.get(j).getId());
                if (loan != null) {
                    loansByBook.add(loan);
                }
            }

            Date returnDate = null;
            if (!loansByBook.isEmpty()) {
                for (int k = 0; k < loansByBook.size(); k++) {
                    Date endDate = loansByBook.get(k).getEndDate();
                    if (returnDate == null || returnDate.after(endDate)) {
                        returnDate = endDate;
                    }
                }

            }


            bookingInfo.setUser(user.getUsername());
            bookingInfo.setBook(bookClient.displayBook(bookingsByUser.get(i).getBook()).getTitle());
            bookingInfo.setReturnDate(returnDate);
            bookingInfo.setNbRank(rank);

            listBookingsInfo.add(bookingInfo);

        }

        model.addAttribute("listBookingsInfo", listBookingsInfo);

        return "ListBookings";
    }

    @GetMapping("/bookings/add/{bookId}")
    public String addBooking(@PathVariable String bookId) {

        BookingBean booking = new BookingBean();

        int id = Integer.parseInt(bookId);

        if(bookingClient.listBookingByBookOrderByStartDate(id).size() <(bookClient.listCopies(id).size()*2)){

        booking.setBook(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        AccountBean user = accountClient.findUsername(username);
        booking.setUser(user.getId());
        booking.setStartDate(new Date());

        bookingClient.addBooking(booking);

        }

        else{
            logger.info("annulation réservations : liste d'attente déjà complète");
        }

        return "redirect:/bookings";

    }

    @GetMapping("/bookings/delete/{bookingId}")
    public String deleteBooking(@PathVariable String bookingId) {
        int id = Integer.parseInt(bookingId);
        bookingClient.deleteBooking(id);

        return "redirect:/bookings";

    }

}
