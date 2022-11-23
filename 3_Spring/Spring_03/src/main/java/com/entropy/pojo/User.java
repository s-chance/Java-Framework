package com.entropy.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // 注册到Spring容器中
public class User {

    private String name;

    public String getName() {
        return name;
    }

    @Value("boot")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
