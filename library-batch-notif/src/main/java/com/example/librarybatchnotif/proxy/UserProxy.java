package com.example.librarybatchnotif.proxy;

import com.example.librarybatchnotif.model.AccountBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "zuul-server")
@FeignClient(name = "zuul-server", url = "http://localhost:9004/")
@RibbonClient(name = "auth-server")
public interface UserProxy {

    @GetMapping(value = "/auth-server/batch/account/{id}")
    AccountBean selectAccount(@PathVariable int id);


}
