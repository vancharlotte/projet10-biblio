package com.example.librarybatchnotif.proxy;

import com.example.librarybatchnotif.model.LoanBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "zuul-server", url = "http://localhost:9004/")
@RibbonClient(name = "library-loan")
public interface LoanProxy {


    @GetMapping(value = "/library-loan/batch/loanNotReturnedOnTime")
    List<LoanBean> listLoanNotReturnedOnTime();


}
