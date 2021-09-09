package com.example.librarybook.controller;

import com.example.librarybook.exception.BookNotFoundException;
import com.example.librarybook.model.Book;
import com.example.librarybook.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class BookRestControllerTest {


    @InjectMocks
    private BookRestController bookRestController;

    @Mock
    private BookService bookServiceMock;

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
    public void listBooksTest() {
        Mockito.when(bookServiceMock.findAll()).thenReturn(books);
        assertEquals(books, bookRestController.listBooks());
    }

    @Test
    public void searchBooksTest() {
        when(bookServiceMock.findByString("titre")).thenReturn(books);
        assertEquals(books, bookRestController.searchBooks("titre"));
        assertNotEquals(books, bookRestController.searchBooks("untitled"));

    }

    @Test
    public void displayBookTest() {
        when(bookServiceMock.findById(1)).thenReturn(books.get(0));
        assertEquals(books.get(0), bookRestController.displayBook(1));
        assertNotEquals(books.get(1), bookRestController.displayBook(1));

    }


        //expected throw "book not found"
    @Test
    public void displayBookExceptionTest() {
        assertThrows(BookNotFoundException.class, () -> bookRestController.displayBook(3));
    }

}