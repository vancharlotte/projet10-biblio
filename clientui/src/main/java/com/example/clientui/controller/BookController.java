package com.example.clientui.controller;

import com.example.clientui.beans.AccountBean;
import com.example.clientui.beans.BookBean;
import com.example.clientui.beans.BookingBean;
import com.example.clientui.beans.CopyBean;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);


    @Autowired
    private LibraryAccountClient accountClient;

    @Autowired
    private LibraryBookClient bookClient;

    @Autowired
    private LibraryLoanClient loanClient;

    @Autowired
    private LibraryBookingClient bookingClient;

    @GetMapping("/books/page/{currentPage}")
    public String searchBooks(@PathVariable int currentPage,
                              @RequestParam(name = "word", defaultValue = "") String word, Model model) {
        int pageSize = 5;
        int totalPages;
        List<BookBean> books;

        if (word.equals("")) {
            List<BookBean> allBooks = bookClient.listBooks();
            totalPages = (allBooks.size() / pageSize) + (allBooks.size() % pageSize == 0 ? 0 : 1);
            books = bookClient.findBooksPaginated(currentPage, pageSize);

        } else {
            List<BookBean> allBooks = bookClient.searchBooks(word);
            totalPages = (allBooks.size() / pageSize) + (allBooks.size() % pageSize == 0 ? 0 : 1);
            books = bookClient.getBooks(currentPage, pageSize, word);

        }
        model.addAttribute("books", books);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("word", word);

        return "SearchBooks";
    }


    @GetMapping("/books/{id}")
    public String selectBook(@PathVariable int id, Model model) {
        BookBean book = bookClient.displayBook(id);
        model.addAttribute("book", book);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        AccountBean user = accountClient.findUsername(username);

        List<CopyBean> copies = bookClient.listCopies(book.getId());
        logger.info(copies.toString());

        List<CopyBean> copiesAvailable = new ArrayList<>();

        boolean loanExist=false;
        for (int i=0; i<copies.size(); i++) {
            if (loanClient.existLoanByCopyAndUserAndNotReturned(copies.get(i).getId(), user.getId())) {
                loanExist = true;

            }
        }

        for (int i = 0; i < copies.size(); i++) {
            boolean copyAvailable = loanClient.copyAvailable(copies.get(i).getId());
            if (copyAvailable) {
                copiesAvailable.add(copies.get(i));
            }
        }

        List<BookingBean> bookings = bookingClient.listBookingByBookOrderByStartDate(book.getId());


        model.addAttribute("nbCopy", copies.size());
        model.addAttribute("nbCopyAvailable", copiesAvailable.size());

        model.addAttribute("nbBooking", bookings.size());

        if (!bookings.isEmpty()) {
            model.addAttribute("returnDate", bookings.get(0).getStartDate());
        } else {
            model.addAttribute("returnDate", null);
        }



        boolean bookingExist = bookingClient.existByUserAndBook(user.getId(), book.getId());
        boolean completelist = bookings.size() >= (copies.size() * 2);



        if (bookingExist || loanExist) {
            model.addAttribute("refusedBooking", true);
        }
        if (completelist ) {
            model.addAttribute("completedBooking", true);
        }

        bookingClient.listBookingByBookOrderByStartDate(book.getId());


        return "Book";
    }

}
