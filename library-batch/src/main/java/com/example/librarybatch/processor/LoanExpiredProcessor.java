package com.example.librarybatch.processor;

import com.example.librarybatch.model.LoanBean;
import com.example.librarybatch.proxy.UserProxy;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class LoanExpiredProcessor implements ItemProcessor<LoanBean, LoanBean> {

    @Autowired
    UserProxy userProxy;

    /*@Autowired*/

    @Override
    @Cacheable("loan")
    public LoanBean process(LoanBean loanBean) throws Exception {

        System.out.println("item processor");
        String email = (userProxy.selectAccount(loanBean.getUser())).getEmail();

        loanBean.setUserEmail(email);
        System.out.println(loanBean.getUserEmail());
        return loanBean;
    }


}
