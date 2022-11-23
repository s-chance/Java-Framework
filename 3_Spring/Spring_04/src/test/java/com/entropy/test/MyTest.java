package com.entropy.test;

import com.entropy.mapper.UserMapper;
import com.entropy.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyTest {
    @Test
    public void test1() throws IOException {
        // 原生MyBatis配置, 需要在MyBatis里完整配置才能使用
        String resource = "mybatis-config.xml";
        InputStream resourceAsStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory build = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = build.openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        List<User> users = mapper.queryUser();
        for (User user : users) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    @Test
    public void test2() {
        // 测试时需要注释掉原来mybatis-config的配置, 否则会冲突
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        for (User user : userMapper.queryUser()) {
            System.out.println(user);
        }
    }

    @Test
    public void test3() {
        // 另一种方式整合MyBatis
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapperPro = context.getBean("userMapperPro", UserMapper.class);
        List<User> users = userMapperPro.queryUser();
        for (User user : users) {
            System.out.println(user);
        }
    }

    // 事务管理异常测试
    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapperPro", UserMapper.class);
        List<User> users = userMapper.queryUser(); // 这里的方法进行了改造, 不仅仅是查询
        for (User user : users) {
            System.out.println(user);
        }
    }
}
