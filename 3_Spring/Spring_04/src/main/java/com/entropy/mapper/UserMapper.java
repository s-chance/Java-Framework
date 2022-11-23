package com.entropy.mapper;

import com.entropy.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> queryUser();

    public int addUser(User user);

    public int deleteUser(int id);
}
