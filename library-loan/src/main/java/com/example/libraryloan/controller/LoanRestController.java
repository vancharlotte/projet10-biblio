package com.example.libraryloan.controller;

import com.example.libraryloan.dao.LoanDao;
import com.example.libraryloan.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import java.time.LocalDate;

import java.sql.Date;
import java.util.List;


@RestController
public class LoanRestController {

    Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    @Autowired
    LoanDao loanDao;

    //addLoan
    @PostMapping(value = "/loan")
        public ResponseEntity<Void> addLoan(@RequestBody Loan loan) {
        Loan loanAdded =   loanDao.save(loan);
        if (loanAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(loanAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    //endLoan or //renewLoan
    @PutMapping(value="/loan")
    public void endLoan(@RequestBody Loan loan){
        loanDao.save(loan);
    }


    //listLoanNotReturned
    @GetMapping(value ="/loanNotReturnedOnTime")
    public List<Loan> listLoanNotReturnedOnTime(){
        LocalDate localDate = LocalDate.now();
        logger.info(Date.valueOf(localDate).toString());
        return loanDao.findByEndDateLessThanAndReturnedFalse(Date.valueOf(localDate));
    }

    @GetMapping(value ="/loans/{user}")
    public List<Loan> listLoans(@PathVariable int user){
        return loanDao.findByUser(user);
    }



}

