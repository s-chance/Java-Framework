package com.entropy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/t1")
    public String t1() {
        System.out.println("TestController执行t1方法");
        return "OK";
    }
}
