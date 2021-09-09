package com.example.librarybooking.controller;

import com.example.librarybooking.exception.BookingNotFoundException;
import com.example.librarybooking.model.Booking;
import com.example.librarybooking.service.BookingService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookingRestControllerTest {

    @InjectMocks
    private BookingRestController bookingRestController;

    @Mock
    private BookingService bookServiceMock;

    private static List<Booking> bookings = new ArrayList<>();

    @BeforeAll
    public static void createListBookings() throws ParseException {
        Booking booking1 = new Booking();
        booking1.setId(1);
        booking1.setBook(1);
        booking1.setUser(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(LocalDate.now().minusDays(2).toString());
        booking1.setStartDate(date);
        booking1.setNotifDate(null);

        Booking booking2 = new Booking();
        booking2.setId(2);
        booking2.setBook(2);
        booking2.setUser(2);
        booking2.setStartDate(new Date());
        booking2.setNotifDate(null);

        bookings.add(booking1);
        bookings.add(booking2);

    }


    @Test
    public void selectBookingTest() {
        Mockito.when(bookServiceMock.findById(1)).thenReturn(bookings.get(0));
        assertEquals(bookings.get(0),bookingRestController.selectBooking(1));
        assertNotEquals(bookings.get(1), bookingRestController.selectBooking(1));
    }

    @Test
    public void selectBookingExceptionTest() {
        Mockito.when(bookServiceMock.findById(1)).thenReturn(null);
        assertThrows(BookingNotFoundException.class, () -> bookingRestController.selectBooking(1));
    }

    @Test
    public void findBookingByUserByBookTest (){
        Mockito.when(bookServiceMock.findByUserAndBook(1,1)).thenReturn(bookings.get(0));
        assertEquals(bookings.get(0),bookingRestController.findBookingByUserByBook(1,1));
        assertNotEquals(bookings.get(0),bookingRestController.findBookingByUserByBook(2,1));

        Mockito.when(bookServiceMock.findByUserAndBook(1,5)).thenReturn(null);
        assertEquals(null,bookingRestController.findBookingByUserByBook(1,5));
    }

    @Test
    public void existByUserAndBookTest(){
        Mockito.when(bookServiceMock.existByUserAndBook(1,1)).thenReturn(true);
        assertTrue(bookingRestController.existByUserAndBook(1,1));

        Mockito.when(bookServiceMock.existByUserAndBook(1,5)).thenReturn(false);
        assertFalse(bookingRestController.existByUserAndBook(1,5));

    }



    @Test
    public void addBookingTest() {
       // test if booking est null

        //  else si pas null :
            // booking.setStartDate(new Date());
           //  bookingService.saveOrUpdate(booking);
    }

    @Test
    public void deleteBookingTest(){
        // bookingService.deleteBooking(id);
    }


    // update booking when send notif client
    @Test
    public void notifBookingTest() {
       // booking.setNotifDate(new Date());

        //bookingService.saveOrUpdate(booking);

    }

    @Test
    public void listBookingByUserTest(){
        Mockito.when(bookServiceMock.findByUser(1)).thenReturn(bookings);
        assertEquals(bookings, bookingRestController.listBookingByUser(1));

        Mockito.when(bookServiceMock.findByUser(5)).thenReturn(null);
        assertNull( bookingRestController.listBookingByUser(5));
    }

    @Test
    public void listBookingByBookTest(){
        Mockito.when(bookServiceMock.findByBook(1)).thenReturn(bookings);
        assertEquals(bookings, bookingRestController.listBookingByBook(1));

        Mockito.when(bookServiceMock.findByBook(5)).thenReturn(null);
        assertNull( bookingRestController.listBookingByBook(5));

    }

    @Test
    public void deleteBookingExpiredTest(){
        // bookingService.deleteBooking(id);
    }

    @Test
    public void notifNextBookingtTest(){
        //Booking booking = bookingService.findById(id);
      //  booking.setNotifDate(new Date());
         // bookingService.saveOrUpdate(booking);
    }

    @Test
    public void listBookingByNotifDateExpiredTest(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-2);
        Mockito.when(bookServiceMock.findByNotifDateExpired(c.getTime())).thenReturn(bookings);
        assertEquals(bookings, bookingRestController.listBookingByNotifDateExpired());

    }

    @Test
    public void listBookingByBookOrderByStartDateTest(){
        Mockito.when(bookServiceMock.findByBookOrderByStartDate(1)).thenReturn(bookings);
        assertEquals(bookings,bookingRestController.listBookingByBookOrderByStartDate(1));
    }
}
