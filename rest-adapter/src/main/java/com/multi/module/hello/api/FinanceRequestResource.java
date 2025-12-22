package com.multi.module.hello.api;

import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.domain.financeRequest.model.FinanceRequest;
import com.multi.module.financeRequest.FinanceRequestDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
public class FinanceRequestResource {

    @Autowired
    private FinanceRequestDomain financeRequestDomain;

    @GetMapping
    public String sendLimitExpiry() {

        financeRequestDomain.triggerEmail();
        return "hello from finance";
    }
}

