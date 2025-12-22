package com.multi.module.hello.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@RequiredArgsConstructor
public class HelloResource {

    @GetMapping
    public String sayHello(){
        return "hello from the other side!!";
    }

//    side@GetMapping
//    public void triggerEmail(){
//        requestNotification.sendEmail(
//        )
//    }
}
