package com.entropy.springboot02jpa.service;

import com.entropy.springboot02jpa.dao.UserMapper;
import com.entropy.springboot02jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
