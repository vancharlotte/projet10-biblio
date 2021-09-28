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
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public static void createListBooks() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate1 = simpleDateFormat.parse(LocalDate.now().minusDays(26).toString());
        Date endDate1 = simpleDateFormat.parse(LocalDate.now().plusDays(2).toString());

        Date startDate2 = simpleDateFormat.parse(LocalDate.now().minusDays(30).toString());
        Date endDate2 = simpleDateFormat.parse(LocalDate.now().minusDays(2).toString());

        Loan loan1 = new Loan();
        loan1.setId(1);
        loan1.setUser(1);
        loan1.setCopy(1);
        loan1.setStartDate(startDate1);
        loan1.setEndDate(endDate1);
        loan1.setRenewed(false);
        loan1.setReturned(false);

        Loan loan2 = new Loan();
        loan2.setId(2);
        loan2.setUser(1);
        loan2.setCopy(1);
        loan2.setStartDate(startDate2);
        loan2.setEndDate(endDate2);
        loan2.setRenewed(false);
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

   @Test
    public void addLoan() throws ParseException {
        assertEquals(ResponseEntity.noContent().build(), loanRestController.addLoan(null));

        Loan loan3 = new Loan();
        loan3.setUser(1);
        loan3.setCopy(3);

       MockHttpServletRequest request = new MockHttpServletRequest();
       RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
               .buildAndExpand(loan3.getId())
               .toUri();
        assertEquals(ResponseEntity.created(location).build(), loanRestController.addLoan(loan3));


    }

    @Test
    public void renewLoan() throws ParseException {
        // if endDate after now : renew
        Loan loan3 = new Loan();
        loan3.setRenewed(true);
        Mockito.when(loanServiceMock.renew(loans.get(0))).thenReturn(loan3);
        assertTrue(loanRestController.renewLoan(loans.get(0)).isRenewed());

        // si endDate before now : not renew
        assertFalse(loanRestController.renewLoan(loans.get(1)).isRenewed());

    }


    @Test
    public void returnLoan(){
        Loan loan3 = new Loan();
        loan3.setReturned(true);
        Mockito.when(loanServiceMock.findById(loans.get(0).getId())).thenReturn(loans.get(0));
        Mockito.when(loanServiceMock.returnLoan(loans.get(0))).thenReturn(loan3);
        assertTrue(loanRestController.returnLoan(loans.get(0).getId()).isReturned());
    }

    @Test
    public void listLoanNotReturnedOnTime()  {
        Mockito.when(loanServiceMock.findByEndDateLessThanAndReturnedFalse()).thenReturn(loans);
        assertEquals(loans,loanRestController.listLoanNotReturnedOnTime());
    }

    @Test
    public void listLoans(){
        Mockito.when(loanServiceMock.findByUser(1)).thenReturn(loans);
        assertEquals(loans,loanRestController.listLoans(1));

    }

    @Test
    public void getLoanByCopyAndReturnedNot(){
        Mockito.when(loanServiceMock.findByCopyAndReturnedNot(1)).thenReturn(loans.get(0));
        assertEquals(loans.get(0),loanRestController.getLoanByCopyAndReturnedNot(1));
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
