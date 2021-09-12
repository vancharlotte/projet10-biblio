package com.example.authserver.service;

import com.example.authserver.model.Account;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @InjectMocks
    UserDetailServiceImpl userDetailService;

    @Mock
    private AccountService accountServiceMock;


    @Test
    public void loadUserByUsernameTest(){
        Account user = new Account();
        user.setUsername("username");
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setEmail("aaa@aaa.com");
        user.setFirstName("user");
        user.setLastName("lastname");
        user.setPassword("password");
        user.setPhoneNumber("0600000000");
        user.setRoles("user");



        Mockito.when(accountServiceMock.findByUsername("username")).thenReturn(Optional.of(user));

        assertNotNull(userDetailService.loadUserByUsername("username"));


    }

    @Test
    public void loadUserByUsernameExceptionTest(){
        assertThrows(UsernameNotFoundException.class, ()->
            userDetailService.loadUserByUsername("unknown")
        );

    }
}
