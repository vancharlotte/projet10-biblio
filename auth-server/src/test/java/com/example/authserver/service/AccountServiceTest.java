package com.example.authserver.service;

import com.example.authserver.model.Account;
import com.example.authserver.repository.AccountRepository;
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
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepositoryMock;

    private static List<Account> accounts = new ArrayList<>();

    @BeforeAll
    public static void createListAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();

        accounts.add(account1);
        accounts.add(account2);

    }


    @Test
    public void findAllTest(){
        Mockito.when(accountRepositoryMock.findAll()).thenReturn(accounts);
        assertEquals(accounts, accountService.findAll());
        assertNotEquals(new ArrayList<>(), accountService.findAll());

    }

    @Test
    public void findByUsernameTest(){
        Mockito.when(accountRepositoryMock.findByUsername("user")).thenReturn(java.util.Optional.ofNullable(accounts.get(0)));
        assertEquals(java.util.Optional.ofNullable(accounts.get(0)), accountService.findByUsername("user"));
        assertNotEquals(java.util.Optional.ofNullable(accounts.get(1)), accountService.findByUsername("user"));

        //not null but optional Empty
        assertNotNull(accountService.findByUsername("user123"));

    }

    @Test
    public void findByIdTest(){
        Mockito.when(accountRepositoryMock.findById(0)).thenReturn(accounts.get(0));
        assertEquals(accounts.get(0), accountService.findById(0));
        assertNotEquals(accounts.get(1), accountService.findById(0));
        assertNull(accountService.findById(5));
    }
}
