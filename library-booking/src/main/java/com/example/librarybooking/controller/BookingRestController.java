package com.example.librarybooking.controller;

import com.example.librarybooking.exception.BookingNotFoundException;
import com.example.librarybooking.model.Booking;
import com.example.librarybooking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

import java.net.URI;
import java.util.Date;
import java.util.List;


@RestController
public class BookingRestController {

    Logger logger = LoggerFactory.getLogger(BookingRestController.class);

    @Autowired
    BookingService bookingService;

    @GetMapping(value="/booking/{id}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Booking selectBooking(@PathVariable int id) {
        Booking booking = bookingService.findById(id);
        if(booking==null) throw new BookingNotFoundException("booking not found");
        return bookingService.findById(id);
    }

    @GetMapping(value="/bookings/find/{user}/{book}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Booking findBookingByUserByBook (@PathVariable int user, @PathVariable int book){
        return bookingService.findByUserAndBook(user,book);

    }

    @GetMapping(value="/bookings/exist/{user}/{book}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public boolean existByUserAndBook(@PathVariable int user,@PathVariable int book){
        return bookingService.existByUserAndBook(user,book);
    }



    @PostMapping(value = "/addBooking")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public ResponseEntity<Void> addBooking(@Valid @RequestBody Booking booking) {


        if ((booking == null)||(bookingService.existByUserAndBook(booking.getUser(), booking.getBook()))){
            return ResponseEntity.noContent().build();}

        else {

            booking.setStartDate(new Date());
            bookingService.saveOrUpdate(booking);


            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(booking.getId())
                    .toUri();


            return ResponseEntity.created(location).build();


        }
    }

    @PutMapping(value = "/bookings/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public void deleteBooking(@PathVariable int id){
        bookingService.deleteBooking(id);
    }



 // update booking when send notif client
    @PostMapping(value = "/notifBooking")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void notifBooking(@Valid @RequestBody Booking booking) {
            booking.setNotifDate(new Date());
            bookingService.saveOrUpdate(booking);

    }

    @PostMapping(value = "/batch/notifNextBooking/{id}")
    public void notifNextBooking(@PathVariable int id){
        Booking booking = bookingService.findById(id);
        booking.setNotifDate(new Date());
        bookingService.saveOrUpdate(booking);
    }


    @GetMapping(value ="/bookings/user/{user}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByUser(@PathVariable int user){
        return bookingService.findByUser(user);
    }

    //@GetMapping(value ="/bookings/{book}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByBook(@PathVariable int book){
        return bookingService.findByBook(book);
    }


    @PutMapping(value = "/batch/bookings/delete/expired/{id}")
    public void deleteBookingExpired(@PathVariable int id){
        bookingService.deleteBooking(id);
    }


    @GetMapping(value ="/batch/bookings/expired")
//    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByNotifDateExpired(){
        return bookingService.findByNotifDateExpired();
    }

    @GetMapping(value = {"/batch/bookings/book/{book}", "/bookings/book/{book}"})
   // @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByBookOrderByStartDate(@PathVariable int book){
        return bookingService.findByBookOrderByStartDate(book);
    }




}

