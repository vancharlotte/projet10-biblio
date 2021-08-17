package com.example.clientui.controller;

import com.example.clientui.beans.*;
import com.example.clientui.client.LibraryAccountClient;
import com.example.clientui.client.LibraryBookClient;
import com.example.clientui.client.LibraryLoanClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class LoanController {

    private Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LibraryLoanClient loanClient;

    @Autowired
    private LibraryBookClient bookClient;

    @Autowired
    private LibraryAccountClient accountClient;

    @GetMapping("/loans")
    public String ListLoans(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        AccountBean user = accountClient.findUsername(username);

        List<LoanBean> loans = loanClient.listLoans(user.getId());
        List<LoanInformation> listLoansInfo = new ArrayList<>();

        for (int i = 0; i < loans.size(); i++) {

            LoanInformation loanInformation = new LoanInformation();

            loanInformation.setLoanId(loans.get(i).getId());

            CopyBean copy= bookClient.selectCopy(loans.get(i).getCopy());
            BookBean book = bookClient.displayBook(copy.getBook());
            loanInformation.setBookTitle(book.getTitle());

            loanInformation.setStartDate(loans.get(i).getStartDate());
            loanInformation.setEndDate(loans.get(i).getEndDate());
            loanInformation.setRenewed(loans.get(i).isRenewed());
            loanInformation.setReturned(loans.get(i).isReturned());

            boolean late = false;
            LocalDate now = LocalDate.now(ZoneId.of("Europe/Paris"));
            LocalDate endDate = loans.get(i).getEndDate().toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDate();
            if(!now.isBefore(endDate)){
                late = true;
            }

            loanInformation.setLate(late);

            listLoansInfo.add(loanInformation);
        }

        model.addAttribute("listLoansInfo", listLoansInfo);
        return "ListLoans";
    }


    @GetMapping( value="/loan/renew/{id}")
    public String renewLoan(@PathVariable int id){
        LoanBean loan =  loanClient.selectLoan(id);
        loanClient.renewLoan(loan);
        logger.info("put loan");
        return "redirect:/loans";

    }

}
