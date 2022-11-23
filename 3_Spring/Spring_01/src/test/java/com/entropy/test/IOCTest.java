package com.entropy.test;

import com.entropy.dao.Impl.MySQLImpl;
import com.entropy.dao.Impl.OracleImpl;
import com.entropy.pojo.User;
import com.entropy.service.Impl.UserServiceImpl;
import com.entropy.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IOCTest {

    @Test
    public void test() {
        UserService userService = new UserServiceImpl();

        //IOC控制反转思想
        userService.setUserDao(new MySQLImpl());
        userService.getUser();
        userService.setUserDao(new OracleImpl());
        userService.getUser();
    }
}
