package com.entropy.test;

import com.entropy.pojo.User;
import com.entropy.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {
    public static void main(String[] args) {
        //获取ApplicationContext, 即Spring中的内容
        ApplicationContext beans = new ClassPathXmlApplicationContext("beans.xml");

        //通过Spring创建对象
        User user = (User) beans.getBean("user");

        System.out.println(user);
        user.show();

        //通过Spring实现控制反转
        UserService userService = (UserService) beans.getBean("userService");
        //如果想修改具体实现类, 只需要在xml文件中修改ref的属性值即可
        userService.getUser();

        //IOC有参构造
        //通过下标实现
        User userAllArgsConstructor = (User) beans.getBean("userConstructorByIndex");
        userAllArgsConstructor.show();

        //通过类型实现
        User userConstructorByType = (User) beans.getBean("userConstructorByType");
        userConstructorByType.show();

        //通过参数名实现
        User userConstructorByName = (User) beans.getBean("userConstructorByName");
        userConstructorByName.show();

        System.out.println(user == beans.getBean("user"));

        //通过别名获取对象
        User user2 = (User) beans.getBean("user2");
        user2.show();

        //通过p命名空间获取对象
        User p = beans.getBean("p", User.class);
        p.show();

        //通过c命名空间获取对象
        User c = beans.getBean("c", User.class);
        c.show();

        User c1 = beans.getBean("c", User.class);

        System.out.println(c == c1);
    }
}
