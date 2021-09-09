package com.example.librarybooking.service;

import com.example.librarybooking.dao.BookingDao;
import com.example.librarybooking.model.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingDao bookingDaoMock;

    private static List<Booking> bookings = new ArrayList<>();

    @BeforeAll
    public static void createListBookings() throws ParseException {
        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setBook(1);
        booking1.setUser(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        Date date = simpleDateFormat.parse(LocalDate.now().plusDays(2).toString());
        booking1.setStartDate(date);
        booking1.setNotifDate(null);

        Booking booking2 = new Booking();
        booking1.setId(2);
        booking1.setBook(2);
        booking1.setUser(2);
        booking1.setStartDate(new Date());
        booking1.setNotifDate(null);

        bookings.add(booking1);
        bookings.add(booking2);
    }

    @Test
    public void saveOrUpdateTest() {
        // bookingDao.save(booking);
    }

    @Test
    public void findByIdTest() {
        Mockito.when(bookingDaoMock.findById(1)).thenReturn(bookings.get(0));
        // bookingDao.findById(id);
    }

    @Test
    public void findByUserTest() {
        // bookingDao.findByUser(user);
    }

    @Test
    public void findByBookTest() {
        // bookingDao.findByBook(book);
    }

    @Test
    public void findByNotifDateTest() {
        // bookingDao.findByNotifDate(date);
    }

    @Test
    public void findByNotifDateExpiredTest() {
        // bookingDao.findByNotifDateExpired(date);

    }

    @Test
    public void findByBookOrderByStartDateTest() {
        // bookingDao.findByBookOrderByStartDate(book);
    }

    @Test
    public void deleteBookingTest() {
        //bookingDao.deleteById(id);
    }

    @Test
    public void existByUserAndBookTest() {
       // Booking exist = bookingDao.findByUserAndBook(user,book);
        /*
        test if exist null return false
        test if already exist return true
         */
    }

    @Test
    public void findByUserAndBook() {
        // bookingDao.findByUserAndBook(user,book);

    }
}
