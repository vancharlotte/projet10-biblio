package com.example.librarybatch.processor;

import com.example.librarybatch.model.LoanBean;
import com.example.librarybatch.proxy.UserProxy;
import com.example.librarybatch.writer.LoanExpiredItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class LoanExpiredProcessor implements ItemProcessor<LoanBean, LoanBean> {

    private Logger logger = LoggerFactory.getLogger(LoanExpiredProcessor.class);

    @Autowired
    UserProxy userProxy;

    /*@Autowired*/

    @Override
    @Cacheable("loan")
    public LoanBean process(LoanBean loanBean) throws Exception {

        logger.info("item processor");
        String email = (userProxy.selectAccount(loanBean.getUser())).getEmail();

        loanBean.setUserEmail(email);
        logger.info(loanBean.getUserEmail());
        return loanBean;
    }


}
