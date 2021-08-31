package com.example.librarybatchnotif.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "zuul-server", url = "http://localhost:9004/")
@RibbonClient(name = "library-book")
public interface BookProxy {

}
