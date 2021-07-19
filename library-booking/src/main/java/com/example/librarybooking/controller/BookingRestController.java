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



    @PutMapping(value = "/booking/return")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Booking returnBooking(@Valid @RequestBody Booking booking) {
        return bookingService.returnBooking(booking);
    }


    @GetMapping(value ="/bookings/{user}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByUser(@PathVariable int user){
        return bookingService.findByUser(user);
    }

    @GetMapping(value ="/bookings/{book}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Booking> listBookingByBook(@PathVariable int book){
        return bookingService.findByBook(book);
    }

    @PutMapping(value = "/booking/return")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public void deleteBooking(@Valid @RequestBody Booking booking) {
         bookingService.deleteBooking(booking);
    }


}

