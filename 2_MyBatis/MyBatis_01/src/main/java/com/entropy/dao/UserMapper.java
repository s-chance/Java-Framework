package com.entropy.dao;

import com.entropy.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    //查询全部信息
    List<User> searchAllUser();

    //根据id查询信息
    User searchUserById(Integer id);

    //新增用户信息
    int addUser(User user);

    //修改用户信息
    int updateUser(User user);

    //删除用户信息
    int deleteUser(Integer id);

    //根据map新增用户信息
    int addUserWithMap(Map<String, Object> map);

    //模糊查询
    List<User> fuzzySearch(String value);

    //分页查询
    List<User> searchWithPage(Map<String, Integer> map);

    //RowBounds分页
    List<User> searchByRowBounds();
}
