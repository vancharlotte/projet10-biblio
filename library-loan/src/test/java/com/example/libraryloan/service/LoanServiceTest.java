package com.example.libraryloan.service;

import com.example.libraryloan.dao.LoanDao;
import com.example.libraryloan.exception.LoanNotFoundException;
import com.example.libraryloan.model.Loan;
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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanDao loanDaoMock;

    private static List<Loan> loans = new ArrayList<>();

    @BeforeAll
    public static void createListLoans() {
        Loan loan1 = new Loan();
        loan1.setId(1);
        loan1.setUser(1);
        loan1.setCopy(1);
        loan1.setStartDate(new Date());
        loan1.setEndDate(new Date());
        loan1.setRenewed(false);
        loan1.setReturned(false);

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
        Loan loan3 = new Loan();
        loan3.setId(3);
        loan3.setUser(1);
        loan3.setCopy(3);
        loan3.setStartDate(new Date());
        loan3.setEndDate(new Date());
        loan3.setRenewed(true);
        loan3.setReturned(false);

        Mockito.when(loanDaoMock.save(Mockito.any(Loan.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        assertEquals(loan3,loanService.saveOrUpdate(loan3));
    }

   @Test
    public void findByEndDateLessThanAndReturnedFalseTest() {
       // Date date = new Date();
        Mockito.when(loanDaoMock.findByEndDateLessThanAndReturnedFalse(new Date())).thenReturn(loans);
        assertEquals(loans, loanService.findByEndDateLessThanAndReturnedFalse());
    }

    @Test
    public void findByIdTest() {
        Mockito.when(loanDaoMock.findById(1)).thenReturn(loans.get(0));
        assertEquals(loans.get(0), loanService.findById(1));
    }


    @Test
    public void findByUserTest() {
        Mockito.when(loanDaoMock.findByUser(1)).thenReturn(loans);
        assertEquals(loans, loanService.findByUser(1));
    }


    @Test
    public void findByCopyAndReturnedNotTest() {
        Mockito.when(loanDaoMock.findByCopyAndReturned(1,false)).thenReturn(loans.get(0));
        assertEquals(loans.get(0), loanService.findByCopyAndReturnedNot(1));

    }

    @Test
    public void copyAvailableTest() {
        Mockito.when(loanDaoMock.existsByCopyAndReturned(1, false)).thenReturn(false);
        assertTrue(loanService.copyAvailable(1));

        Mockito.when(loanDaoMock.existsByCopyAndReturned(1, false)).thenReturn(true);
        assertFalse(loanService.copyAvailable(1));

    }

    @Test
    public void renewTest() {
        Loan loan3 = loans.get(0);
        loan3.setRenewed(true);
        Mockito.when(loanDaoMock.findById(1)).thenReturn(loans.get(0));
        Mockito.when(loanDaoMock.findById(2)).thenReturn(loans.get(1));
        Mockito.when(loanDaoMock.save(loans.get(0))).thenReturn(loan3);
        Mockito.when(loanDaoMock.save(loans.get(1))).thenReturn(loans.get(1));

        assertTrue(loanService.renew(loans.get(0)).isRenewed());
        assertTrue(loanService.renew(loans.get(1)).isRenewed());

    }

    @Test
    public void renewExceptionTest() {
        Mockito.when(loanDaoMock.findById(2)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanService.renew(loans.get(1)));

    }


    @Test
    public void returnLoanTest() {
        Loan loan3 = loans.get(0);
        loan3.setReturned(true);
        Mockito.when(loanDaoMock.findById(1)).thenReturn(loans.get(0));
        Mockito.when(loanDaoMock.save(loans.get(0))).thenReturn(loan3);
        assertTrue(loanService.returnLoan(loans.get(0)).isReturned());

    }

    @Test
    public void returnLoanExceptionTest() {
        Mockito.when(loanDaoMock.findById(2)).thenReturn(null);
        assertThrows(LoanNotFoundException.class, () -> loanService.returnLoan(loans.get(1)));
    }

    @Test
    public void existByCopyAndUserAndReturnedNotTest() {
        Mockito.when(loanDaoMock.findByUserAndCopyAndReturnedNot(1,1,false)).thenReturn(loans.get(1));
        assertTrue(loanService.existByCopyAndUserAndReturnedNot(1,1));

        Mockito.when(loanDaoMock.findByUserAndCopyAndReturnedNot(1,1,false)).thenReturn(null);
        assertFalse(loanService.existByCopyAndUserAndReturnedNot(1,1));


    }
}
