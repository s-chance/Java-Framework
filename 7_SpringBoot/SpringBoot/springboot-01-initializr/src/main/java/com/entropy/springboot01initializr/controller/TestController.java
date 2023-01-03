package com.entropy.springboot01initializr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/initializr")
    public String initializr() {
        return "spring initializr";
    }
}
