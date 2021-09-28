package com.example.libraryloan.dao;

import com.example.libraryloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer> {


    List<Loan> findByEndDateLessThanAndReturnedFalse(Date date);

    Loan findById(int id);

    List<Loan> findByUser(int user);

    Loan findByCopyAndReturned(int copy, boolean returned);

    boolean existsByCopyAndReturned(int copy, boolean returned);


    Loan findByUserAndCopyAndReturnedNot(int user, int copy, boolean returned);
}
