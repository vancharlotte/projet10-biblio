package com.example.librarybooking.service;

import com.example.librarybooking.dao.BookingDao;
import com.example.librarybooking.exception.BookingNotFoundException;
import com.example.librarybooking.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {


    private Logger logger = LoggerFactory.getLogger(BookingService.class);


    @Autowired
    private BookingDao bookingDao;

    public Booking saveOrUpdate(Booking booking) {
        return bookingDao.save(booking);
    }


    public Booking findById(int id) {
        return bookingDao.findById(id);
    }

    public List<Booking> findByUser(int user) {
        return bookingDao.findByUser(user);
    }

    public List<Booking> findByBook(int book) {
        return bookingDao.findByBook(book);
    }

    public void deleteBooking(int id) {
        bookingDao.deleteById(id);
    }


    public Booking returnBooking (Booking booking) {
        Booking exist = bookingDao.findById(booking.getId());
        if (exist == null) throw new BookingNotFoundException("booking not found");
        return bookingDao.save(booking);
    }
}
