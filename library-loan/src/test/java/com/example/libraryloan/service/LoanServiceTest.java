package com.example.libraryloan.service;

import com.example.libraryloan.dao.LoanDao;
import com.example.libraryloan.exception.LoanNotFoundException;
import com.example.libraryloan.model.Loan;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDaoMock;

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
    public void saveOrUpdateTest() {
    }

    @Test
    public void findByEndDateLessThanAndReturnedFalseTest() {
        Mockito.when(loanDaoMock.findByEndDateLessThanAndReturnedFalse(new Date())).thenReturn(loans);
        assertEquals(loans, loanService.findByEndDateLessThanAndReturnedFalse(new Date()));
    }

    @Test
    public void findByIdTest() {
        Mockito.when(loanDaoMock.findById(1)).thenReturn(loans.get(0));
        assertEquals(1, loanService.findById(1).getCopy());
    }

  /*  @Test
    public void findByIdExceptionTest() {
        Mockito.when(loanDaoMock.findById(3)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanService.findById(3));
    }*/

    @Test
    public void findByUserTest() {
        Mockito.when(loanDaoMock.findByUser(1)).thenReturn(loans);
        assertEquals(2, loanService.findByUser(1).size());
    }

  /*  @Test
    public void findByUserExceptionTest() {
        Mockito.when(loanDaoMock.findByUser(1)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanService.findByUser(1));
    }*/

    @Test
    public void findByCopyAndReturnedNotOrderByEndDateTest() {
        Mockito.when(loanDaoMock.findByCopyAndReturnedNotOrderByEndDate(1,true)).thenReturn(loans);
        assertEquals(loans, loanService.findByCopyAndReturnedNotOrderByEndDate(1));

    }

    @Test
    public void copyAvailableTest() {
        Mockito.when(loanDaoMock.existsByCopyAndReturned(1, false)).thenReturn(false);
        assertEquals(true, loanService.copyAvailable(1));

        Mockito.when(loanDaoMock.existsByCopyAndReturned(1, false)).thenReturn(true);
        assertEquals(false, loanService.copyAvailable(1));

    }

    @Test
    public void renewTest() {
        Mockito.when(loanDaoMock.findById(1)).thenReturn(loans.get(0));
        loans.get(0).setRenewed(true);
        Mockito.when(loanDaoMock.save(loans.get(0))).thenReturn(loans.get(0));
        assertEquals(loans.get(0).isRenewed(), loanService.renew(loans.get(0)).isRenewed());

        loans.get(0).setRenewed(false);

    }

    @Test
    public void renewExceptionTest() {
        Mockito.when(loanDaoMock.findById(2)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanService.renew(loans.get(1)));

    }


    @Test
    public void returnLoanTest() {
        Mockito.when(loanDaoMock.findById(2)).thenReturn(loans.get(1));
        loans.get(1).setReturned(true);
        Mockito.when(loanDaoMock.save(loans.get(1))).thenReturn(loans.get(1));
        assertEquals(loans.get(1).isReturned(), loanService.returnLoan(loans.get(1)).isReturned());

        loans.get(1).setReturned(false);
    }

    @Test
    public void returnLoanExceptionTest() {
        Mockito.when(loanDaoMock.findById(2)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanService.returnLoan(loans.get(1)));
    }

    @Test
    public void existByCopyAndUserAndReturnedNotTest() {

    }
}
