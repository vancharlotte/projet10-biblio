package com.example.authserver.controller;

import com.example.authserver.exception.UserNotFoundException;
import com.example.authserver.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Test
    public void selectAccountTest() {
       // return accountService.findById(id);
    }

    @Test
    public void selectAccountExceptionTest() {
        // Account account = accountService.findById(id);
        //  if (account == null) throw new UserNotFoundException("user not found");
        //  return account;
    }

    @Test
    public void findUsernameTest() {
       // Optional<Account> account = accountService.findByUsername(username);
       // return account.get();
    }
}
