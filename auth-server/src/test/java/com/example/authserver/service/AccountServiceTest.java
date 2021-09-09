package com.example.authserver.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @BeforeAll
    public static void createListAccounts() {

    }


    @Test
    public void findAllTest(){

        //accountRepository.findAll();
    }

    @Test
    public void findByUsernameTest(){
        // accountRepository.findByUsername(username);
    }

    @Test
    public void findByIdTest(){
        //accountRepository.findById(id);
    }
}
