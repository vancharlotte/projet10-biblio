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

import static org.junit.jupiter.api.Assertions.*;


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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(LocalDate.now().plusDays(2).toString());
        booking1.setStartDate(date);
        booking1.setNotifDate(null);

        Booking booking2 = new Booking();
        booking1.setId(2);
        booking1.setBook(2);
        booking1.setUser(1);
        booking1.setStartDate(new Date());
        booking1.setNotifDate(null);

        bookings.add(booking1);
        bookings.add(booking2);
    }

    @Test
    public void saveOrUpdateTest() {
        Booking booking3 = new Booking();
        Mockito.when(bookingDaoMock.save(Mockito.any(Booking.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        assertEquals(booking3, bookingService.saveOrUpdate(booking3));
    }

    @Test
    public void findByIdTest() {
        Mockito.when(bookingDaoMock.findById(1)).thenReturn(bookings.get(0));
        assertEquals(bookings.get(0), bookingService.findById(1));
        assertNull(bookingService.findById(5));
    }

    @Test
    public void findByUserTest() {
        Mockito.when(bookingDaoMock.findByUser(1)).thenReturn(bookings);
        assertEquals(bookings, bookingService.findByUser(1));
    }

    @Test
    public void findByBookTest() {
        Mockito.when(bookingDaoMock.findByBook(1)).thenReturn(bookings);
        assertEquals(bookings, bookingService.findByBook(1));
        assertNotEquals(bookings, bookingService.findByBook(5));
    }

    @Test
    public void findByNotifDateTest() throws ParseException {
        Date date1 = new Date();
        Mockito.when(bookingDaoMock.findByNotifDate(date1)).thenReturn(bookings);
        assertEquals(bookings, bookingService.findByNotifDate(date1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = simpleDateFormat.parse(LocalDate.now().plusDays(2).toString());
        assertNotEquals(bookings, bookingService.findByNotifDate(date2));
    }

    @Test
    public void findByNotifDateExpiredTest() throws ParseException {
        Date date1 = new Date();
        Mockito.when(bookingDaoMock.findByNotifDateExpired(date1)).thenReturn(bookings);
        assertEquals(bookings, bookingService.findByNotifDateExpired(date1));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = simpleDateFormat.parse(LocalDate.now().plusDays(2).toString());
        assertNotEquals(bookings, bookingService.findByNotifDateExpired(date2));
    }

    @Test
    public void findByBookOrderByStartDateTest() {
        Mockito.when(bookingDaoMock.findByBookOrderByStartDate(1)).thenReturn(bookings);
        assertEquals(bookings, bookingService.findByBookOrderByStartDate(1));
        assertEquals(new ArrayList<>(), bookingService.findByBookOrderByStartDate(5));
        // bookingDao.findByBookOrderByStartDate(book);
    }

    @Test
    public void deleteBookingTest() {
        //bookingDao.deleteById(id);
    }

    @Test
    public void existByUserAndBookTest() {
        Mockito.when(bookingDaoMock.findByUserAndBook(1,1)).thenReturn(bookings.get(0));
        assertTrue(bookingService.existByUserAndBook(1,1));
        Mockito.when(bookingDaoMock.findByUserAndBook(1,5)).thenReturn(null);
        assertFalse(bookingService.existByUserAndBook(1,5));

    }

    @Test
    public void findByUserAndBook() {
        Mockito.when(bookingDaoMock.findByUserAndBook(1,1)).thenReturn(bookings.get(0));
        assertEquals(bookings.get(0),bookingService.findByUserAndBook(1,1));
        Mockito.when(bookingDaoMock.findByUserAndBook(1,5)).thenReturn(null);
        assertNull(bookingService.findByUserAndBook(1,5));
    }
}
