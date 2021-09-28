package com.example.clientui.client;

import com.example.clientui.beans.LoanBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "zuul-server", url="http://localhost:9004/")
@RibbonClient(name = "library-loan")
public interface LibraryLoanClient {

    @GetMapping(value="/library-loan/loan/{id}")
    LoanBean selectLoan(@PathVariable int id);

    @PutMapping(value = "/library-loan/loan/renew")
    LoanBean renewLoan(@RequestBody LoanBean loan);

    @GetMapping(value = "/library-loan/loans/{user}")
    List<LoanBean> listLoans(@PathVariable int user);

    @GetMapping(value ="/library-loan/loansByCopy/{copy}")
    LoanBean getLoanByCopyAndReturnedNot(@PathVariable int copy);

    @GetMapping(value ="/library-loan/loansByCopyAndUserAndReturnedNot/{copy}/{user}")
    boolean existLoanByCopyAndUserAndNotReturned(@PathVariable int copy, @PathVariable int user);

    @GetMapping(value = "/library-loan/loans/copyAvailable/{copy}")
    boolean copyAvailable(@PathVariable int copy);

    @PutMapping(value = "/library-loan/loan/return/{loanId}")
    LoanBean returnLoan(@PathVariable int loanId);

    @PostMapping(value = "/library-loan/loan")
    ResponseEntity<Void> addLoan(@Valid @RequestBody LoanBean loan);



}