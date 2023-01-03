package com.entropy.springboot03redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    // 实现序列化接口, 否则无法使用redis存储
    private Integer id;
    private String name;
}
