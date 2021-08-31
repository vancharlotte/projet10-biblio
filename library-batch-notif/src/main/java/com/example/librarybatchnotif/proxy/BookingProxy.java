package com.example.librarybatchnotif.proxy;

import com.example.librarybatchnotif.model.BookingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@FeignClient(name = "zuul-server", url = "http://localhost:9004/")
@RibbonClient(name = "library-booking")
public interface BookingProxy {


    @GetMapping(value = "/library-booking/bookings/expired")
    List<BookingBean> listBookingByNotifDateExpired();

    @PutMapping(value = "/library-booking/bookings/delete/expired/{id}")
    void deleteBookingExpired(@PathVariable int id);

    @GetMapping(value = "/library-booking/bookings/book/{book}")
    List<BookingBean> listBookingByBookOrderByStartDate(@PathVariable int book);

    @PostMapping(value = "/library-booking/notifNextBooking/{id}")
    void notifNextBooking(@PathVariable int id);

}
