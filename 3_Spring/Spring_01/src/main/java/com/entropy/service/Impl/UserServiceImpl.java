package com.entropy.service.Impl;

import com.entropy.dao.Impl.MySQLImpl;
import com.entropy.dao.Impl.OracleImpl;
import com.entropy.dao.UserDao;
import com.entropy.service.UserService;

public class UserServiceImpl implements UserService {

    //原始方式
//    private UserDao mysqlDao = new MySQLImpl();
//    private UserDao oracleDao = new OracleImpl();

    //IOC控制反转思想
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void getUser() {
        userDao.getUser();
    }
}
