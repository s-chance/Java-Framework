package com.entropy.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {

    //SqlSessionFactory是专门用来创建SqlSession对象(即操作数据库的对象)
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            //获取配置文件信息
            String resource = "mybatis-config.xml";
            InputStream resourceStream = Resources.getResourceAsStream(resource);
            //通过配置文件获取一个初始化的SqlSessionFactory对象
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取SqlSession对象
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);
    }
}
