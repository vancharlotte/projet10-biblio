package com.example.librarybatchnotif.proxy;

import com.example.librarybatchnotif.model.BookingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Date;
import java.util.List;

@FeignClient(name = "zuul-server", url = "http://localhost:9004/")
@RibbonClient(name = "library-booking")
public interface BookingProxy {


    @GetMapping(value ="/library-booking/bookings/expired")
    List<BookingBean> listBookingByNotifDateExpired();

    @PutMapping(value = "/library-booking/bookings/delete/expired/{id}")
    void deleteBookingExpired(@PathVariable int id);

}
