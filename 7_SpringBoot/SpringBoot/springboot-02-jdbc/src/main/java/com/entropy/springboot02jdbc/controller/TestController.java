package com.entropy.springboot02jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 查询所有信息
    // 直接使用map接收数据
    @GetMapping("/userList")
    public List<Map<String, Object>> userList() {
        String sql = "select * from user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    // 新增
    @GetMapping("addUser")
    public String addUser() {
        String sql = "insert into user(id,name,password) values (6,'entropy','rt33')";
        jdbcTemplate.update(sql);
        return "OK";
    }

    // 修改
    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id) {
        String sql = "update user set name=?,password=? where id=" + id;
        Object[] objects = new Object[2];
        objects[0] = "admin";
        objects[1] = "sudoers";
        jdbcTemplate.update(sql, objects);
        return "update OK";
    }

    // 删除
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        String sql = "delete from user where id=?";
        jdbcTemplate.update(sql, id);
        return "delete OK";
    }
}
