package com.entropy.config;

import com.entropy.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // @Configuration专门用于指定配置类, 即用来替代xml配置的类
@ComponentScan("com.entropy.pojo") // 在配置类中指定扫描包
@Import(OtherConfig.class) // 导入OtherConfig配置类
public class MyConfig {
    // 通过@Bean注解注册Bean, 相当于在xml中编写bean标签
    // 方法名相当于bean标签中的id属性
    // 返回值相当于bean标签中的class属性
    @Bean
    public User getUser() {
        return new User();
    }
}
