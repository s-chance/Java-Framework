package com.entropy.springboot01initializr.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
/*
@ConfigurationProperties作用：
将配置文件中配置的每一个属性的值，映射到这个组件中；
告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定
参数 prefix = “person” : 将配置文件中的person下面的所有属性一一对应
*/
@ConfigurationProperties(prefix = "person")
//@PropertySource(value = "classpath:person.properties")
public class Person {
//    @Value("${name}")
    private String name;
    private Integer age;
    private Boolean holiday;
    private Date birth;
    private Map<String, Object> map;
    private List<Object> list;
    private Cat cat;
}
