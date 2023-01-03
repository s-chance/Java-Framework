package com.entropy.springboot02jpa.controller;


import com.entropy.springboot02jpa.entity.User;
import com.entropy.springboot02jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> findAll() {
        return userService.findAll();
    }
}
