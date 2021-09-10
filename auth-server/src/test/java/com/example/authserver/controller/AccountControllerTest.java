package com.example.authserver.controller;

import com.example.authserver.exception.UserNotFoundException;
import com.example.authserver.model.Account;
import com.example.authserver.service.AccountService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountServiceMock;

    private static List<Account> accounts = new ArrayList<>();

    @BeforeAll
    public static void createListAccounts() {
        Account account1 = new Account();
        Account account2 = new Account();

        accounts.add(account1);
        accounts.add(account2);

    }

    @Test
    public void selectAccountTest() {
        Mockito.when(accountServiceMock.findById(1)).thenReturn(accounts.get(0));
        assertEquals(accounts.get(0),accountController.selectAccount(1));
    }

    @Test
    public void selectAccountExceptionTest() {
        Mockito.when(accountServiceMock.findById(1)).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> accountController.selectAccount(1));
    }

    @Test
    public void findUsernameTest() {
        Mockito.when(accountServiceMock.findByUsername("user")).thenReturn(java.util.Optional.ofNullable(accounts.get(0)));
        assertEquals(accounts.get(0),accountController.findUsername("user"));
    }

    @Test
    public void findUsernameExceptionTest() {
        Mockito.when(accountServiceMock.findByUsername("user")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> accountController.findUsername("user"));
    }
}
