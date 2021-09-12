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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    private BookingService bookingServiceMock;

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
        Mockito.when(bookingServiceMock.findById(1)).thenReturn(bookings.get(0));
        assertEquals(bookings.get(0),bookingRestController.selectBooking(1));
        assertNotEquals(bookings.get(1), bookingRestController.selectBooking(1));
    }

    @Test
    public void selectBookingExceptionTest() {
        Mockito.when(bookingServiceMock.findById(1)).thenReturn(null);
        assertThrows(BookingNotFoundException.class, () -> bookingRestController.selectBooking(1));
    }

    @Test
    public void findBookingByUserByBookTest (){
        Mockito.when(bookingServiceMock.findByUserAndBook(1,1)).thenReturn(bookings.get(0));
        assertEquals(bookings.get(0),bookingRestController.findBookingByUserByBook(1,1));
        assertNotEquals(bookings.get(0),bookingRestController.findBookingByUserByBook(2,1));

        Mockito.when(bookingServiceMock.findByUserAndBook(1,5)).thenReturn(null);
        assertEquals(null,bookingRestController.findBookingByUserByBook(1,5));
    }

    @Test
    public void existByUserAndBookTest(){
        Mockito.when(bookingServiceMock.existByUserAndBook(1,1)).thenReturn(true);
        assertTrue(bookingRestController.existByUserAndBook(1,1));

        Mockito.when(bookingServiceMock.existByUserAndBook(1,5)).thenReturn(false);
        assertFalse(bookingRestController.existByUserAndBook(1,5));

    }



    @Test
    public void addBookingTest() {
        assertEquals(ResponseEntity.noContent().build(), bookingRestController.addBooking(null));

        Booking booking3 =new Booking();

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(booking3.getId())
                .toUri();
        assertEquals(ResponseEntity.created(location).build(), bookingRestController.addBooking(booking3));
    }

    @Test
    public void deleteBookingTest(){
        int expected = 0;
        bookingRestController.deleteBooking(expected);
        Mockito.verify(bookingServiceMock).deleteBooking(expected);
    }

  @Test
  public void deleteBookingExpiredTest(){
        int expected = 0;
        bookingRestController.deleteBooking(expected);
        Mockito.verify(bookingServiceMock).deleteBooking(expected);
  }


    @Test
    public void notifBookingTest() {
        Booking booking3 = bookings.get(0);
        Mockito.when(bookingServiceMock.saveOrUpdate(booking3)).thenReturn(booking3);
        bookingRestController.notifBooking(bookings.get(0));
        assertNotNull(booking3.getNotifDate());

    }

    @Test
    public void notifNextBookingtTest(){
        Booking booking3 = bookings.get(0);
        Mockito.when(bookingServiceMock.findById(bookings.get(0).getId())).thenReturn(booking3);
        Mockito.when(bookingServiceMock.saveOrUpdate(booking3)).thenReturn(booking3);
        bookingRestController.notifNextBooking(bookings.get(0).getId());
        assertNotNull(booking3.getNotifDate());

    }

    @Test
    public void listBookingByUserTest(){
        Mockito.when(bookingServiceMock.findByUser(1)).thenReturn(bookings);
        assertEquals(bookings, bookingRestController.listBookingByUser(1));

        Mockito.when(bookingServiceMock.findByUser(5)).thenReturn(null);
        assertNull( bookingRestController.listBookingByUser(5));
    }

    @Test
    public void listBookingByBookTest(){
        Mockito.when(bookingServiceMock.findByBook(1)).thenReturn(bookings);
        assertEquals(bookings, bookingRestController.listBookingByBook(1));

        Mockito.when(bookingServiceMock.findByBook(5)).thenReturn(null);
        assertNull( bookingRestController.listBookingByBook(5));

    }


/*    @Test
    public void listBookingByNotifDateExpiredTest(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,-2);
        Mockito.when(bookingServiceMock.findByNotifDateExpired(c.getTime())).thenReturn(bookings);
        assertEquals(bookings, bookingRestController.listBookingByNotifDateExpired());

    }*/

    @Test
    public void listBookingByBookOrderByStartDateTest(){
        Mockito.when(bookingServiceMock.findByBookOrderByStartDate(1)).thenReturn(bookings);
        assertEquals(bookings,bookingRestController.listBookingByBookOrderByStartDate(1));
    }
}
