package com.entropy.dao;

import com.entropy.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select * from user")
    List<User> getAll();

    //@Param注解的作用是给参数命名, 参数命名后就能根据名字得到参数值, 准确地将参数传递给sql语句
    //当只有一个参数时可以不写@Param注解, 当存在多个参数时则必须在所有参数前面加@Param注解
    @Select("select * from user where id = #{id}")
    User getById(@Param("id") Integer id);

    @Insert("insert into user(id,name,password) values (#{id},#{name},#{password})")
    int add(User user);

    @Update("update user set name=#{name},password=#{password} where id=#{id}")
    int update(User user);

    @Delete("delete from user where id = #{id}")
    int delete(@Param("id") Integer id);
}
