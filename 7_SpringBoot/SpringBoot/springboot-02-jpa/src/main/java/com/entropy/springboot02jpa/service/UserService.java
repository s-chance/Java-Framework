package com.entropy.springboot02jpa.service;

import com.entropy.springboot02jpa.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
