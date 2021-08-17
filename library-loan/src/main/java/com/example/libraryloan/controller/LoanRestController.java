package com.example.libraryloan.controller;

import com.example.libraryloan.exception.LoanNotFoundException;
import com.example.libraryloan.model.Loan;
import com.example.libraryloan.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;


@RestController
public class LoanRestController {

    Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    @Autowired
    LoanService loanService;

    @GetMapping(value="/loan/{id}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Loan selectLoan(@PathVariable int id) {
        Loan loan = loanService.findById(id);
        if(loan==null) throw new LoanNotFoundException("loan not found");
        return loanService.findById(id);
    }

    //addLoan (for now, new loan added by employee only)
    @PostMapping(value = "/loan")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> addLoan(@Valid @RequestBody Loan loan) {

        if (loan == null){
            return ResponseEntity.noContent().build();}

        else {
            LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
            LocalDateTime nowMidnight = LocalDateTime.of(now, LocalTime.MIDNIGHT);
            Timestamp timestamp = Timestamp.valueOf(nowMidnight);
            logger.info(timestamp.toString());

            loan.setStartDate(timestamp);
            loan.setEndDate(Timestamp.valueOf(nowMidnight.plusDays(28)));
            loan.setReturned(false);
            loan.setRenewed(false);
            loanService.saveOrUpdate(loan);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(loan.getId())
                    .toUri();
            return ResponseEntity.created(location).build();
        }
    }


    //renewLoan
    @PutMapping(value = "/loan/renew")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Loan renewLoan(@Valid @RequestBody Loan loan){
        LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
        LocalDate endDate = loan.getEndDate().toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDate();
        if(!now.isBefore(endDate)){
            return loan;
        }
        else{
            return loanService.renew(loan);
        }
    }


    //returnLoan (for now, loan returned by employee only)
    @PutMapping(value = "/loan/return")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Loan returnLoan(@Valid @RequestBody Loan loan) {
        return loanService.returnLoan(loan);
    }


    //listLoanNotReturned
    @GetMapping(value ="/batch/loanNotReturnedOnTime")
    @PreAuthorize("isAuthenticated()")
    public List<Loan> listLoanNotReturnedOnTime(){
        LocalDate today = LocalDate.now(ZoneId.of("Europe/Paris"));
        LocalDateTime todayMidnight = LocalDateTime.of(today, LocalTime.MIDNIGHT);
        Timestamp timestamp = Timestamp.valueOf(todayMidnight);
        logger.info(timestamp.toString());

        return loanService.findByEndDateLessThanAndReturnedFalse(timestamp);
    }

    @GetMapping(value ="/loans/{user}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Loan> listLoans(@PathVariable int user){
        return loanService.findByUser(user);
    }

    @GetMapping(value = "/loans/copyAvailable/{copy}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public boolean copyAvailable(@PathVariable int copy){
        return loanService.copyAvailable(copy);
    }


}

