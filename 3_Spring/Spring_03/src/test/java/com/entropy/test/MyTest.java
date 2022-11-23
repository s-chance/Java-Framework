package com.entropy.test;

import com.entropy.config.MyConfig;
import com.entropy.pojo.User;
import com.entropy.proxy.service.UserService;
import com.entropy.proxy.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        // 使用配置类的方式来实现, 就需要通过AnnotationConfigApplicationContext来获取容器
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        User user = context.getBean("getUser", User.class);

        System.out.println(user.getName());
    }

    // aop测试
    // 通过Spring API实现
    @Test
    public void test1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        // aop动态代理返回的是接口, 接口的代理已经在xml中配置实现
        UserService userService = applicationContext.getBean("userService", UserService.class);

        userService.delete();
    }
    // 通过自定义类实现
    @Test
    public void test2() {
        // 测试自定义类配置时, 需要先注释掉xml中的其它aop配置, 反之同理
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }
    // 通过注解实现
    @Test
    public void test3() {
        // 测试注解配置时, 需要先注释掉xml中的其它aop配置, 反之同理
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();
    }
}
