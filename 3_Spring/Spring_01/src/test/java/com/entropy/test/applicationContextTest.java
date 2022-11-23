package com.entropy.test;

import com.entropy.pojo.People;
import com.entropy.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class applicationContextTest {
    public static void main(String[] args) {
        //获取beans和beans2两个配置的对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.getUser();

        UserService otherUserService = (UserService) applicationContext.getBean("otherUserService");
        otherUserService.getUser();

        //获取applicationContext中的people对象
        People people = (People) applicationContext.getBean("people");
        System.out.println(people);
    }
}
