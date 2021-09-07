package com.example.librarybook.controller;


import com.example.librarybook.exception.CopyNotFoundException;
import com.example.librarybook.model.Copy;
import com.example.librarybook.service.CopyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CopyRestControllerTest {

    @InjectMocks
    private CopyRestController copyRestController;

    @Mock
    private CopyService copyServiceMock;

    private static List<Copy> copies = new ArrayList<>();


    @BeforeAll
    public static void createListCopies() {
        Copy copy1 = new Copy();
        copy1.setId(1);
        copy1.setBook(1);

        Copy copy2 = new Copy();
        copy2.setId(2);
        copy2.setBook(1);

        copies.add(copy1);
        copies.add(copy2);

    }

    @Test
    public void listCopiesTest() {
        Mockito.when(copyServiceMock.findByBook(1)).thenReturn(copies);
        assertEquals(2, copyRestController.listCopies(1).size());
    }

    @Test
    public void selectCopiesTest() {
        Mockito.when(copyServiceMock.findById(1)).thenReturn(copies.get(0));
        assertEquals(1, copyRestController.selectCopy(1).getBook());
    }

    //expected throw "copy not found"
    @Test
    public void displayBookExceptionTest() {
        assertThrows(CopyNotFoundException.class, () -> copyRestController.selectCopy(3));
    }




}
