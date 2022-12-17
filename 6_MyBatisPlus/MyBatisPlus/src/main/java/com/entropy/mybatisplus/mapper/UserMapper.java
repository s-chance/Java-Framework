package com.entropy.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.entropy.mybatisplus.pojo.User;
import org.springframework.stereotype.Repository;

@Repository //持久层
public interface UserMapper extends BaseMapper<User> {
    //直接继承BaseMapper即可实现所有的基本的CRUD操作
    //需要在主启动类配置扫描mapper包注解@MapperScan
}
