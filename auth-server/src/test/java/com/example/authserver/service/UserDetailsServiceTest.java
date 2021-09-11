package com.example.authserver.service;

import com.example.authserver.model.Account;
import com.example.authserver.model.AuthUserDetail;
import com.example.authserver.repository.AccountRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @InjectMocks
    UserDetailServiceImpl userDetailService;

    @Mock
    private AccountService accountServiceMock;




    @Test
    public void loadUserByUsernameTest(){
      /*  Account user = new Account();
        user.setUsername("username");


        Mockito.when(accountServiceMock.findByUsername("username")).thenReturn(Optional.of(user));
        Mockito.when(new AccountStatusUserDetailsChecker().check(accountServiceMock.findByUsername("username"))).thenReturn();


        assertNotNull(userDetailService.loadUserByUsername("username"));
        assertNull(userDetailService.loadUserByUsername("error"));

*/

    }

    @Test
    public void loadUserByUsernameExceptionTest(){

    }
}
