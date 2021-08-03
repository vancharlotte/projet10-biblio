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
import java.util.Date;
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
        List<BookingInformation> listBookingsInfo = null;

        for (int i = 0; i < bookingsByUser.size(); i++) {

            BookingInformation bookingInfo = new BookingInformation();


            BookBean book = bookClient.displayBook(bookingsByUser.get(i).getBook());

            List<CopyBean> copyByBook = bookClient.listCopies(book.getId());
            List<LoanBean> loanByCopy = null;

            for (int j = 0; j< copyByBook.size(); j++){
                List<LoanBean> loanByCopyByBook = loanClient.listLoansByCopyAndReturnedNot(copyByBook.get(j).getId());
                loanByCopy.addAll(loanByCopyByBook);
            }

            Date date1 = null;
            for (int k = 0; k< loanByCopy.size(); k++){
                Date date2 = loanByCopy.get(k).getEndDate();
                if(date2.before(date1) || date1==null ){ date1 =date2; }

        }

            int rank = 0;
            List<BookingBean> bookingByBook = bookingClient.listBookingByBook(book.getId());
            for (int l = 0; l< bookingByBook.size(); l++){
                if (bookingByBook.get(l).getUser()==user.getId()){
                    rank = l+1;
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


}
