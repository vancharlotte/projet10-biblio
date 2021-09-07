package com.example.libraryloan.controller;

import com.example.libraryloan.dao.LoanDao;
import com.example.libraryloan.model.Loan;
import com.example.libraryloan.service.LoanService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LoanRestControllerTest {

    @InjectMocks
    private LoanRestController loanRestController;

    @Mock
    private LoanService loanServiceMock;

    private static List<Loan> loans = new ArrayList<>();

    @BeforeAll
    public static void createListBooks() {
        Loan loan1 = new Loan();
        loan1.setId(1);
        loan1.setUser(1);
        loan1.setCopy(1);
        loan1.setStartDate(new Date());
        loan1.setEndDate(new Date());
        loan1.setRenewed(false);
        loan1.setReturned(true);

        Loan loan2 = new Loan();
        loan2.setId(2);
        loan2.setUser(1);
        loan2.setCopy(1);
        loan2.setStartDate(new Date());
        loan2.setEndDate(new Date());
        loan2.setRenewed(true);
        loan2.setReturned(false);

        loans.add(loan1);
        loans.add(loan2);
    }

    @Test
    public void selectLoanTest(){ }

    @Test
    public void addLoan(){ }

    @Test
    public void renewLoan(){}

    @Test
    public void returnLoan(){}

    @Test
    public void listLoanNotReturnedOnTime(){ }

    @Test
    public void listLoans(){ }

    @Test
    public void listLoansByCopyAndReturnedNot(){
    }

    @Test
    public void existLoanByCopyAndUserAndNotReturned(){
         }

    @Test
    public void copyAvailable(){    }


}
