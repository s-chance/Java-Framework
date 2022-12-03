package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {

    // 存储用户信息, 跳转到登录成功页面
    @RequestMapping("/login")
    public String login(HttpSession session, String username, String password) {
        session.setAttribute("loginInfo", username + " : " + password);
        return "success";
    }

    // 跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    // 跳转到成功登录页面
    @RequestMapping("/success")
    public String main() {
        return "success";
    }

    // 退出登录, 清除用户登录信息
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginInfo");
        return "login";
    }
}
