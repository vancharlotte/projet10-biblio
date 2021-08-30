package com.example.librarybooking.service;

import com.example.librarybooking.dao.BookingDao;
import com.example.librarybooking.exception.BookingNotFoundException;
import com.example.librarybooking.model.Booking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public List<Booking> findByNotifDate(Date date) {
        return bookingDao.findByNotifDate(date);
    }

    public List<Booking> findByNotifDateExpired(Date date) {
        return bookingDao.findByNotifDateExpired(date);
    }



    public List<Booking> findByBookOrderByStartDate(int book) {
        return bookingDao.findByBookOrderByStartDate(book);
    }


    public void deleteBooking(int id) {
        bookingDao.deleteById(id);
    }

    public boolean existByUserAndBook(int user, int book) {
        Booking exist = bookingDao.findByUserAndBook(user,book);
        if (exist == null) {
            logger.info("booking doesn't exist");
            return false;
        }
        else{
            logger.info("booking already exist");
            return true;
        }
    }

    public Booking findByUserAndBook(int user, int book) {
        return bookingDao.findByUserAndBook(user,book);

    }
}
