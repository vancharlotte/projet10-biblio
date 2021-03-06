package com.example.libraryloan.service;

import com.example.libraryloan.dao.LoanDao;
import com.example.libraryloan.exception.LoanNotFoundException;
import com.example.libraryloan.model.Loan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {


    private Logger logger = LoggerFactory.getLogger(LoanService.class);


    @Autowired
    private LoanDao loanDao;

    @Transactional
    public Loan saveOrUpdate(Loan loan) {
        return loanDao.save(loan);
    }

    public List<Loan> findByEndDateLessThanAndReturnedFalse() {
        return loanDao.findByEndDateLessThanAndReturnedFalse(new Date());
    }


    public Loan findById(int id) {
        return loanDao.findById(id);
    }

    public List<Loan> findByUser(int user) {
        return loanDao.findByUser(user);
    }

    public Loan findByCopyAndReturnedNot(int copy) {
        return loanDao.findByCopyAndReturned(copy, false);
    }

    public boolean copyAvailable(int copy) {
        boolean copyAvailable = !loanDao.existsByCopyAndReturned(copy, false);
        logger.info(Boolean.toString(copyAvailable));
        return copyAvailable;
    }

    @Transactional
    public Loan renew(Loan loan) {
        Loan exist = loanDao.findById(loan.getId());
        if (exist == null) throw new LoanNotFoundException("loan not found");
        if (!loan.isRenewed()) {
            loan.setRenewed(true);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(loan.getEndDate());
            calendar.add(Calendar.DAY_OF_YEAR, 28);
            loan.setEndDate(calendar.getTime());
        }

        return loanDao.save(loan);
    }

    @Transactional
    public Loan returnLoan(Loan loan) {
        Loan exist = loanDao.findById(loan.getId());
        if (exist == null) throw new LoanNotFoundException("loan not found");
        exist.setReturned(true);
        return loanDao.save(loan);
    }

    public boolean existByCopyAndUserAndReturnedNot(int copy, int user) {
        Loan exist = loanDao.findByUserAndCopyAndReturnedNot(user,copy, false);
        if (exist!=null){
            logger.info("LL : loan already exist");
            // loan already exist
            return true;}
        else{            logger.info("LL :  loan doesn't exist");
            return false;}

    }
}
