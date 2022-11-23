package com.entropy.service;

import com.entropy.dao.UserDao;

public interface UserService {

    void setUserDao(UserDao userDao);
    void getUser();
}
