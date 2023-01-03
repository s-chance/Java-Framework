package com.entropy.springboot02jpa.dao;

import com.entropy.springboot02jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends JpaRepository<User, Long> {
    /**
     * 这里直接继承JpaRepository
     * 很多方法已经由Jpa实现
     * **/
}
