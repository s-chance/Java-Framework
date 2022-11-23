package com.entropy.mapper;

import com.entropy.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImplPro extends SqlSessionDaoSupport implements UserMapper {
    @Override
    public List<User> queryUser() {
        SqlSession sqlSession = getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 事务管理异常测试
      /*  int id = 6;
        User user = new User(id, "linTa", "7787890");

        int i = userMapper.addUser(user);
        System.out.println("增加用户: " + i);

        System.out.println("异常的删除操作");
        int deleteUser = userMapper.deleteUser(id);*/

        return userMapper.queryUser();
    }

    @Override
    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(id);
    }
}
