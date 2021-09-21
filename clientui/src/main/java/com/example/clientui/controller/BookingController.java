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

    //TODO vérif date
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

            List<CopyBean> copyByBook = bookClient.listCopies(book.getId());
            List<LoanBean> loanByCopy = new ArrayList<>();

            for (CopyBean copyBean : copyByBook) {
                List<LoanBean> loanByCopyByBook = loanClient.listLoansByCopyAndReturnedNot(copyBean.getId());
                loanByCopy.addAll(loanByCopyByBook);
            }

            Date date1 = null;
            for (LoanBean loanBean : loanByCopy) {
                Date date2 = loanBean.getEndDate();
                if (date2.before(date1) || date1 == null) {
                    date1 = date2;

                }
                logger.info("endDate : " + date2);
                logger.info("first endDate : " + date1);

            }

            int rank = 0;
            List<BookingBean> bookingByBook = bookingClient.listBookingByBookOrderByStartDate(book.getId());
            for (int l = 0; l < bookingByBook.size(); l++) {
                if (bookingByBook.get(l).getUser() == user.getId()) {
                    rank = l + 1;
                }
            }

            bookingInfo.setUser(user.getUsername());
            bookingInfo.setBook(bookClient.displayBook(bookingsByUser.get(i).getBook()).getTitle());
            bookingInfo.setReturnDate(date1);
            bookingInfo.setNbRank(rank);

            listBookingsInfo.add(bookingInfo);
        }

        model.addAttribute("listBookingsInfo", listBookingsInfo);

        return "ListBookings";
    }

    //TODO vérif date
    @GetMapping("/bookings/add/{bookId}")
    public String addBooking(@PathVariable String bookId) {

        BookingBean booking = new BookingBean();

        int id = Integer.parseInt(bookId);
        booking.setBook(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        AccountBean user = accountClient.findUsername(username);
        booking.setUser(user.getId());


        LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
        LocalDateTime nowMidnight = LocalDateTime.of(now, LocalTime.MIDNIGHT);
        Timestamp timestamp = Timestamp.valueOf(nowMidnight);

        booking.setStartDate(timestamp);
        logger.info("start Date : " + booking.getStartDate());
        logger.info(new Date().toString());


        bookingClient.addBooking(booking);


        return "redirect:/bookings";

    }

    @GetMapping("/bookings/delete/{bookingId}")
    public String deleteBooking(@PathVariable String bookingId) {
        int id = Integer.parseInt(bookingId);
        bookingClient.deleteBooking(id);

        return "redirect:/bookings";

    }

}
