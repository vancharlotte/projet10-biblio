package com.example.librarybooking.dao;

import com.example.librarybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {


    Booking findById(int id);

    List<Booking> findByUser(int user);

    List<Booking> findByBook(int book);





}
