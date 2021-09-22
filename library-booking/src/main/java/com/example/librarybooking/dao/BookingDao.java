package com.example.librarybooking.dao;

import com.example.librarybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {


    Booking findById(int id);

    List<Booking> findByUser(int user);

    List<Booking> findByBook(int book);

    List<Booking> findByNotifDate(Date date);

    @Query("select b from Booking b where b.notifDate <= :date")
    List<Booking> findByNotifDateExpired(Date date);


    List<Booking> findByBookOrderByStartDate(int book);

    Booking findByUserAndBook(int user, int book);
}
