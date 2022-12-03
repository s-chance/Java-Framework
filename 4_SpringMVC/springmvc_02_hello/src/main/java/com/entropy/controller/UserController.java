package com.entropy.controller;

import com.entropy.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    // produces: 指定响应体返回类型和编码
    @RequestMapping(value = "/json/t1", produces = "application/json;charset=utf-8")
    @ResponseBody // 跳过视图解析器, 直接返回字符串
    public String test1() throws JsonProcessingException {
        // 创建一个jackson的对象映射器用于解析数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 创建一个对象
        User user = new User(1, "测试", 4);
        // 将Java对象转换为json字符串
        String value = objectMapper.writeValueAsString(user);
        return value;
    }
}
