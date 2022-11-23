package com.entropy.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class User {
    @Value("123")
    private String name;

    public String getName() {
        return name;
    }

    @Value("345")
    public void setName(String name) {
        this.name = name;
    }
}
