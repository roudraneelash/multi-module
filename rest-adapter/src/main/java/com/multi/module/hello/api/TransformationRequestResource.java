package com.multi.module.hello.api;

import com.multi.module.transformationRequest.TransformationRequestDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transformation")
public class TransformationRequestResource {
    @Autowired
    private TransformationRequestDomain transformationRequestDomain;

    @GetMapping
    public String sendLimitExpiry() {

        transformationRequestDomain.triggerEmail();
        return "hello from transformation";
    }
}
