package com.example.clientui.client;

import com.example.clientui.beans.BookingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@FeignClient(name = "zuul-server", url="http://localhost:9004/")
@RibbonClient(name = "library-booking")
public interface LibraryBookingClient {


    @GetMapping(value="/library-booking/booking/{id}")
    BookingBean selectBooking(@PathVariable int id);

    //à verif
   // @GetMapping(value="/library-booking/bookings/{user}/{book}")
   // BookingBean findByUserAndBook(@PathVariable int user, int book);

    //@GetMapping(value = "/library-booking/bookings/{book}")
   // List<BookingBean> listBookingByBook(@PathVariable int book);

    @GetMapping(value ="/library-booking/bookings/book/{book}")
    List<BookingBean> listBookingByBookOrderByStartDate(@PathVariable int book);

    @GetMapping(value = "/library-booking/bookings/user/{user}")
    List<BookingBean> listBookingByUser(@PathVariable int user);

    @GetMapping(value ="/library-booking/bookings/{startDate}")
    List<BookingBean> listBookingByStartDate(@PathVariable Date date);

    @GetMapping(value = "/library-booking/booking/delete/{id}")
    boolean deleteBooking(@PathVariable int id);

    @PostMapping(value = "/library-booking/addBooking")
    ResponseEntity<Void> addBooking(@Valid @RequestBody BookingBean booking) ;

    @PostMapping(value = "library-booking/notifBooking")
    void notifBooking(@Valid @RequestBody BookingBean booking);


}
