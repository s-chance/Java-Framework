package com.entropy;

import com.entropy.dao.UserMapper;
import com.entropy.pojo.User;
import com.entropy.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoTest {
    //查询全部信息
    @Test
    public void test1() {
        //获取SqlSession对象
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        //获取mapper对象
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        //执行方法
        List<User> list = userMapper.searchAllUser();

        for (User user : list) {
            System.out.println(user);
        }

        System.out.println();
        //第二种方式, 不推荐
        List<User> objects = sqlSession.selectList("com.entropy.dao.UserDao.searchAllUser");
        for (User object : objects) {
            System.out.println(object);
        }

        //关闭SqlSession
        sqlSession.close();
    }

    //根据id查询信息
    @Test
    public void test2() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        User user = userMapper.searchUserById(1);
        System.out.println(user);

        sqlSession.close();
    }

    //新增用户信息
    @Test
    public void test3() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int rows = userMapper.addUser(new User(4, "banana", "000"));

        System.out.println(rows);

        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    //修改用户信息
    @Test
    public void tes4() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int rows = userMapper.updateUser(new User(4, "peach", "111"));
        System.out.println(rows);

        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    //删除用户信息
    @Test
    public void test5() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        int rows = userMapper.deleteUser(5);
        System.out.println(rows);

        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    //map传参
    @Test
    public void test06() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        Map map = new HashMap<String, Object>();
        map.put("userId", 5);
        map.put("username", "map");
        map.put("password", "0202");
        userMapper.addUserWithMap(map);

        System.out.println(map);

        sqlSession.commit();
        sqlSession.close();
    }

    //模糊查询
    @Test
    public void test07() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        List<User> list = userMapper.fuzzySearch("p");

        for (User user : list) {
            System.out.println(user);
        }

        sqlSession.close();
    }

    static Logger logger = Logger.getLogger(DaoTest.class);

    //日志输出测试
    @Test
    public void test08() {
        logger.info("level:info");
        logger.debug("level:debug");
        logger.error("level:error");
    }
}
