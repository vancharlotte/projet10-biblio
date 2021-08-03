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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@RestController
public class BookingRestController {

    Logger logger = LoggerFactory.getLogger(BookingRestController.class);

    @Autowired
    BookingService bookingService;

    @GetMapping(value="/booking/{id}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Booking selectLoan(@PathVariable int id) {
        Booking booking = bookingService.findById(id);
        if(booking==null) throw new BookingNotFoundException("booking not found");
        return bookingService.findById(id);
    }

    @GetMapping(value="/bookings/{user}/{book}")
    public Booking getBookingByUserByBook (@PathVariable int user, int book){
        Booking booking = bookingService.findByUserAndBook(user,book);
        if(booking==null) throw new BookingNotFoundException("booking not found");
        return bookingService.findByUserAndBook(user,book);

    }


    @PostMapping(value = "/addBooking")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> addBooking(@Valid @RequestBody Booking booking) {

        if (booking == null){
            return ResponseEntity.noContent().build();}

        else {
            LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
            LocalDateTime nowMidnight = LocalDateTime.of(now, LocalTime.MIDNIGHT);
            Timestamp timestamp = Timestamp.valueOf(nowMidnight);
            logger.info(timestamp.toString());

            booking.setStartDate(timestamp);

            bookingService.saveOrUpdate(booking);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(booking.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }

 // update booking when send notif client
    @PostMapping(value = "/notifBooking")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void notifBooking(@Valid @RequestBody Booking booking) {

            LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
            LocalDateTime nowMidnight = LocalDateTime.of(now, LocalTime.MIDNIGHT);
            Timestamp timestamp = Timestamp.valueOf(nowMidnight);
            logger.info(timestamp.toString());

            booking.setNotifDate(timestamp);

            bookingService.saveOrUpdate(booking);

    }


/*
    @PutMapping(value = "/booking/return")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking returnBooking(@Valid @RequestBody Booking booking) {
        return bookingService.returnBooking(booking);
    }
*/

    @GetMapping(value ="/bookings/{user}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByUser(@PathVariable int user){
        return bookingService.findByUser(user);
    }

    //@GetMapping(value ="/bookings/{book}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByBook(@PathVariable int book){
        return bookingService.findByBook(book);
    }

    @GetMapping(value ="/bookings/{notifDate}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByNotifDate(@PathVariable Date date){
        return bookingService.findByNotifDate(date);
    }

    @GetMapping(value ="/bookings/{book}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByBookOrderByStartDate(@PathVariable int book){
        return bookingService.findByBookOrderByStartDate(book);
    }


    @PutMapping(value = "/booking/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public void deleteBooking(@PathVariable int id) {
         bookingService.deleteBooking(id);
    }


}

