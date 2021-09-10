package com.example.libraryloan.controller;

import com.example.libraryloan.exception.LoanNotFoundException;
import com.example.libraryloan.model.Loan;
import com.example.libraryloan.service.LoanService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void selectLoanTest(){
        Mockito.when(loanServiceMock.findById(1)).thenReturn(loans.get(0));
        assertEquals(loans.get(0),loanRestController.selectLoan(1));
    }

    @Test
    public void selectLoanExceptionTest(){
        Mockito.when(loanServiceMock.findById(1)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanRestController.selectLoan(1));
    }

  /*  @Test
    public void addLoan(){ }*/

    @Test
    public void renewLoan() throws ParseException {
        //vérifie si endDATE est pas dépassé d'abord

       /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = simpleDateFormat.parse(LocalDate.now().minusDays(2).toString());
        loans.get(0).setEndDate(date1);
        Loan loan3 = loans.get(0);
        loan3.setRenewed(false);
        Mockito.when(loanServiceMock.renew(loans.get(0))).thenReturn(loan3);
        assertFalse(loanRestController.renewLoan(loans.get(0)).isRenewed());


        Date date2 = simpleDateFormat.parse(LocalDate.now().plusDays(2).toString());
        loans.get(0).setEndDate(date2);
        Loan loan4 = loans.get(0);
        //loan4.setRenewed(true);
        Mockito.when(loanServiceMock.renew(loans.get(0))).thenReturn(loan4);
        assertTrue(loanRestController.renewLoan(loans.get(0)).isRenewed());
        */

    }

    /*
    @Test
    public void returnLoan(){
        Mockito.when(loanServiceMock.returnLoan(loanService.findById(loan));
    }*/

   /* @Test
    public void listLoanNotReturnedOnTime()  {
        Mockito.when(loanServiceMock.findByEndDateLessThanAndReturnedFalse(new Date())).thenReturn(loans);
        assertEquals(2,loanRestController.listLoanNotReturnedOnTime().size());
    }*/

    @Test
    public void listLoans(){
        Mockito.when(loanServiceMock.findByUser(1)).thenReturn(loans);
        assertEquals(loans,loanRestController.listLoans(1));

    }

    @Test
    public void listLoansByCopyAndReturnedNot(){
        Mockito.when(loanServiceMock.findByCopyAndReturnedNotOrderByEndDate(1)).thenReturn(loans);
        assertEquals(loans,loanRestController.listLoansByCopyAndReturnedNot(1));
    }

    @Test
    public void existLoanByCopyAndUserAndNotReturned(){
        // return true if loan already exist
        Mockito.when(loanServiceMock.existByCopyAndUserAndReturnedNot(1, 1)).thenReturn(true);
        assertTrue(loanRestController.existLoanByCopyAndUserAndNotReturned(1,1));
        Mockito.when(loanServiceMock.existByCopyAndUserAndReturnedNot(2, 1)).thenReturn(false);
        assertFalse(loanRestController.existLoanByCopyAndUserAndNotReturned(2,1));

    }

    @Test
    public void copyAvailable(){
        Mockito.when(loanServiceMock.copyAvailable(1)).thenReturn(true);
        assertTrue(loanRestController.copyAvailable(1));
        Mockito.when(loanServiceMock.copyAvailable(2)).thenReturn(false);
        assertFalse(loanRestController.copyAvailable(2));


    }


}
