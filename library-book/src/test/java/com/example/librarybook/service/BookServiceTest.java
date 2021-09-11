package com.example.librarybook.service;

import com.example.librarybook.dao.BookDao;
import com.example.librarybook.model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookDao bookDaoMock;

    private static List<Book> books = new ArrayList<>();


    @BeforeAll
    public static void createListBooks() {
        Book book1 = new Book();
        book1.setId(1);
        book1.setAuthor("auteur");
        book1.setEditor("éditeur");
        book1.setTitle("titre");
        book1.setGenre("genre");
        book1.setLanguage("francais");
        book1.setSummary("résumé");
        book1.setReleaseDate("01-01-2021");

        Book book2 = new Book();
        book2.setId(1);
        book2.setAuthor("auteur");
        book2.setEditor("éditeur");
        book2.setTitle("titre");
        book2.setGenre("genre");
        book2.setLanguage("francais");
        book2.setSummary("résumé");
        book2.setReleaseDate("01-01-2021");

        books.add(book1);
        books.add(book2);
    }

    @Test
    public void findAllTest(){
        Mockito.when(bookDaoMock.findAll()).thenReturn(books);
        assertEquals(books, bookService.findAll());
    }

    @Test
    public void findByIdTest(){
        Mockito.when(bookDaoMock.findById(1)).thenReturn(books.get(0));
        assertEquals(books.get(0), bookService.findById(1));
        assertNull(bookService.findById(5));

    }

    @Test
    public void findByStringTest(){
        when(bookDaoMock.findByTitleOrAuthorOrGenre("titre")).thenReturn(books);
        assertEquals(books, bookService.findByString("titre"));

    }

    @Test
    public void findPaginatedTest(){
        bookService.findPaginated(2,10);

        Pageable pageable = PageRequest.of(1, 10);
        Mockito.verify(bookDaoMock).findAll(pageable);

    }

    @Test
    public void findSearchPaginatedTest(){
        bookService.findSearchPaginated("TITLE",2,10);

        Pageable pageable = PageRequest.of(1, 10);
        Mockito.verify(bookDaoMock).findByTitleOrAuthorOrGenre("title", pageable);
    }



}
