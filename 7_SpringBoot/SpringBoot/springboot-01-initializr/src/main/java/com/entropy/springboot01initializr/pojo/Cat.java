package com.entropy.springboot01initializr.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component //注册bean到容器中
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cat {
    @Value("tom")
    private String name;
    @Value("3")
    private Integer age;
}
