package com.entropy.controller;

import com.entropy.pojo.User;
import com.entropy.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserJSONController {
    @RequestMapping("/json/j1")
    public String json1() throws JsonProcessingException {
        // 创建jackson对象映射器用于解析数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 创建Java对象
        User user = new User(234, "tree", 12);
        // 转换为json字符串
        String value = objectMapper.writeValueAsString(user);
        return value;
    }

    // 输出集合
    @RequestMapping("json/j2")
    public String json2() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<User> list = new ArrayList<User>();

        User a = new User(1, "苹果", 11);
        User b = new User(2, "鸭梨", 12);
        User c = new User(3, "c", 13);
        User d = new User(4, "d", 14);

        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);

        String value = objectMapper.writeValueAsString(list);
        return value;
    }

    // 输出时间(时间戳格式
    @RequestMapping("/json/j3")
    public String json3() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Date date = new Date();
        // 时间对象在jackson中解析后的默认格式为 Timestamp 时间戳
        String value = objectMapper.writeValueAsString(date);
        return value;
    }

    // 输出时间(自定义格式)
    @RequestMapping("/json/j4")
    public String json4() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 不使用时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 自定义时间日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 指定时间日期格式
        objectMapper.setDateFormat(sdf);

        Date date = new Date();
        String value = objectMapper.writeValueAsString(date);
        return value;
    }

    // 另一种自定义时间格式的方式
    @RequestMapping("/json/j5")
    public String json5() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Date date = new Date();
        // 自定义时间日期格式对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return objectMapper.writeValueAsString(sdf.format(date));
    }

    // 引用自己编写的时间日期格式化工具类
    @RequestMapping("/json/j6")
    public String j6() {
        Date date = new Date();
        String json = JsonUtils.getJson(date);
        return json;
    }
}
