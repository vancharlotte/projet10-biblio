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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
public class LoanRestController {

    private Logger logger = LoggerFactory.getLogger(LoanRestController.class);

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
    public ResponseEntity<Void> addLoan(@Valid @RequestBody Loan loan) throws ParseException {

        if (loan == null){
            return ResponseEntity.noContent().build();}

        else {
            loan.setStartDate(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 28);
            loan.setEndDate(calendar.getTime());
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


    @PutMapping(value = "/loan/renew")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Loan renewLoan(@Valid @RequestBody Loan loan){

        Date now = new Date();
        Date endDate = loan.getEndDate();
        if(now.after(endDate)){
            return loan;
        }
        else{
            return loanService.renew(loan);
        }
    }


    //returnLoan (for now, loan returned by employee only)
    @PutMapping(value = "/loan/return/{loanId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Loan returnLoan(@PathVariable int loanId) {
        return loanService.returnLoan(loanService.findById(loanId));
    }


    //listLoanNotReturned
    @GetMapping(value ="/batch/loanNotReturnedOnTime")
    @PreAuthorize("isAuthenticated()")
    public List<Loan> listLoanNotReturnedOnTime(){
        return loanService.findByEndDateLessThanAndReturnedFalse();
    }

    @GetMapping(value ="/loans/{user}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public List<Loan> listLoans(@PathVariable int user){
        return loanService.findByUser(user);
    }

    @GetMapping(value ="/loansByCopy/{copy}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public Loan getLoanByCopyAndReturnedNot(@PathVariable int copy){
        return loanService.findByCopyAndReturnedNot(copy);
    }

    @GetMapping(value ="/loansByCopyAndUserAndReturnedNot/{copy}/{user}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public boolean existLoanByCopyAndUserAndNotReturned(@PathVariable int copy, @PathVariable int user){
        // return true if loan already exist
        return loanService.existByCopyAndUserAndReturnedNot(copy, user);
    }


    @GetMapping(value = "/loans/copyAvailable/{copy}")
    @PreAuthorize("hasAuthority('ADMIN')" + "|| hasAuthority('USER')")
    public boolean copyAvailable(@PathVariable int copy){
        return loanService.copyAvailable(copy);
    }


}

