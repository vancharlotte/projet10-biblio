package com.example.clientui.client;

import com.example.clientui.beans.BookingBean;
import com.example.clientui.beans.LoanBean;
import com.example.librarybooking.model.Booking;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "library-booking")
public interface LibraryBookingClient {


    @GetMapping(value="/library-booking/booking/{id}")
    BookingBean selectBooking(@PathVariable int id);

    @GetMapping(value = "/library-booking/bookings/{book}")
    List<BookingBean> listBookingByBook(@PathVariable int book);

    @GetMapping(value = "/library-booking/bookings/{user}")
    List<BookingBean> listBookingByUser(@PathVariable int user);

    @GetMapping(value = "/library-booking/booking/delete/{id}")
    boolean deleteBooking(@PathVariable int id);

    @PostMapping(value = "/library-booking/addBooking")
    ResponseEntity<Void> addBooking(@Valid @RequestBody Booking booking) ;

    @PostMapping(value = "library-booking/notifBooking")
    void notifBooking(@Valid @RequestBody Booking booking);


}
