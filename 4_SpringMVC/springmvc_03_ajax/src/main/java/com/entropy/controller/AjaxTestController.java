package com.entropy.controller;

import com.entropy.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxTestController {

    @RequestMapping("/a1")
    public void a1(String name, HttpServletResponse response) throws IOException {
        System.out.println(name);
        if ("admin".equals(name)) {
            response.getWriter().print(true);
        } else {
            response.getWriter().print(false);
        }
    }

    @RequestMapping("/a2")
    public List<User> a2() {
        List<User> list = new ArrayList<>();
        list.add(new User("first", 12, "12121"));
        list.add(new User("second", 13, "ababa"));
        list.add(new User("third", 18, "cdcdc"));
        return list; // 返回list的json字符串形式
    }

    @RequestMapping("/a3")
    public String a3(String name, String password) {
        String msg = "";
        // 模拟数据库验证
        String data = "admin";
        if (name != null) {
            if (data.equals(name)) {
                msg = "该名称已存在, 请重新输入";
            } else {
                msg = "PASS";
            }
        }
        if (password != null) {
            if (password.contains(data)) {
                msg = "密码不能包含特定名称, 如admin";
            } else {
                msg = "PASS";
            }
        }
        return msg;
    }
}
