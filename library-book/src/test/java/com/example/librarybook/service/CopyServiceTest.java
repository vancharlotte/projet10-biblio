package com.example.librarybook.service;

import com.example.librarybook.dao.CopyDao;
import com.example.librarybook.model.Copy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CopyServiceTest {

    @InjectMocks
    private CopyService copyService;

    @Mock
    private CopyDao copyDaoMock;

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
    public void findByBookTest(){
        Mockito.when(copyDaoMock.findByBook(1)).thenReturn(copies);
        assertEquals(copies, copyService.findByBook(1));
        assertNull(copyService.findById(1));



    }

    @Test
    public void findByIdTest(){
        Mockito.when(copyDaoMock.findById(1)).thenReturn(copies.get(0));
        assertEquals(copies.get(0), copyService.findById(1));
        assertNotEquals(copies.get(1), copyService.findById(1));
        assertNull(copyService.findById(2));

    }
}
