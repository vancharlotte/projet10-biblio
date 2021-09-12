package com.example.librarybook.controller;

import com.example.librarybook.exception.BookNotFoundException;
import com.example.librarybook.model.Book;
import com.example.librarybook.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/library-book")
public class BookRestController {

    private Logger logger = LoggerFactory.getLogger(BookRestController.class);


    @Autowired
    private BookService bookService;


    @GetMapping(value ="/books", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Book> listBooks (){
        return bookService.findAll();
    }

    @GetMapping("/books/search")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Book> searchBooks(@RequestParam(value = "word", required = false, defaultValue = "") String word) {
        List<Book> listBooks = bookService.findByString(word);
        return listBooks;
    }


    @GetMapping(value="/books/select/{id}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Book displayBook(@PathVariable int id) {
        Book book = bookService.findById(id);
        if(book==null) {
            throw new BookNotFoundException("book not found.");
        }
        return book;
    }


    @GetMapping(value = "/books/search/page/{pageNo}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Book> getBooks(@PathVariable(value = "pageNo") int pageNo,
                            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                            @RequestParam(value = "word", required = false, defaultValue = "") String word) {
        Page<Book> page = bookService.findSearchPaginated(word, pageNo, pageSize);
        return page.getContent();

    }



    @GetMapping("/books/page/{pageNo}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Book> findBooksPaginated(@PathVariable (value = "pageNo") int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        Page<Book> page = bookService.findPaginated(pageNo, pageSize);
        return page.getContent();
    }
}
