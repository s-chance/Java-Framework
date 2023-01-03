package com.entropy.springboot03mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.entropy.springboot03mybatis.dao")
public class Springboot03MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot03MybatisApplication.class, args);
    }

}
