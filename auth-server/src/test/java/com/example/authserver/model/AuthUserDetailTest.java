package com.example.authserver.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AuthUserDetailTest {

    @Test
    public void getAuthoritiesTest() {
        Account user = new Account();
        user.setRoles("user");

        AuthUserDetail authUserDetail = new AuthUserDetail(user);

        assertNotNull(authUserDetail.getAuthorities());

    }

    @Test
    public void getAuthoritiesExceptionTest() {

        AuthUserDetail authUserDetail = null;
        assertThrows(NullPointerException.class, () -> authUserDetail.getAuthorities());

    }

}
