package com.entropy.mapper;

import com.entropy.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper {

//    private SqlSession sqlSession; // 基于MyBatis的SqlSession, 使用SqlSessionTemplate替代
    private SqlSessionTemplate sqlSessionTemplate;

    // 编写set方法用于Spring注入
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> queryUser() {
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        return mapper.queryUser();
    }

    @Override
    public int addUser(User user) {
        return sqlSessionTemplate.getMapper(UserMapper.class).addUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return sqlSessionTemplate.getMapper(UserMapper.class).deleteUser(id);
    }
}
