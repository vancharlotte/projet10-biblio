package com.example.clientui.controller;

import com.example.clientui.beans.BookBean;
import com.example.clientui.beans.CopyBean;
import com.example.clientui.client.LibraryBookClient;
import com.example.clientui.client.LibraryLoanClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {

    private Logger logger = LoggerFactory.getLogger(BookController.class);


    @Autowired
    private LibraryBookClient bookClient;

    @Autowired
    private LibraryLoanClient loanClient;


    @GetMapping("/books/page/{currentPage}")
    public String searchBooks(@PathVariable int currentPage,
                              @RequestParam(name = "word", defaultValue = "") String word, Model model){
        int pageSize = 5;
        int totalPages;
        List<BookBean> books;

        if(word.equals("")){
            List<BookBean> allBooks = bookClient.listBooks();
            totalPages = (allBooks.size()/pageSize)+ (allBooks.size() % pageSize == 0 ? 0 : 1);
            books = bookClient.findBooksPaginated(currentPage, pageSize);

        }
        else{
            List<BookBean> allBooks = bookClient.searchBooks(word);
            totalPages = (allBooks.size()/pageSize)+ (allBooks.size() % pageSize == 0 ? 0 : 1);
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

        List <CopyBean> copies = bookClient.listCopies(book.getId());
        List <CopyBean> copiesAvailable = new ArrayList<>();

       for (int i = 0; i < copies.size(); i++) {
            boolean copyAvailable = loanClient.copyAvailable(copies.get(i).getId());
            if (copyAvailable){
                copiesAvailable.add(copies.get(i));            }
        }

        model.addAttribute("nbCopy", copies.size());
        model.addAttribute("nbCopyAvailable", copiesAvailable.size());

        return "Book";
    }

}
