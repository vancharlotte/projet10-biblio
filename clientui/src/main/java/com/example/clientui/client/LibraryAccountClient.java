package com.example.clientui.client;

import com.example.clientui.beans.AccountBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "auth-server")
public interface LibraryAccountClient {

    @GetMapping(value = "/auth-server/account/{id}")
    AccountBean selectAccount(@PathVariable int id);

    @GetMapping(value = "/auth-server/principal/{username}")
    AccountBean findUsername(@PathVariable String username);


}
