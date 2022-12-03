package com.entropy.controller;

import com.entropy.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataController {

    @RequestMapping("/data/d1")
    public String d1(String name) {
        System.out.println(name);
        return "test";
    }

    // @RequestParam("username")要求请求参数名称必须为username, 类似于给方法参数取别名
    @RequestMapping("/data/d2")
    public String d2(@RequestParam("username") String name) {
        System.out.println(name);
        return "test";
    }

    // 对象数据类型的请求参数名称必须和对象内的成员变量名一致, 否则为null
    @RequestMapping("/data/d3")
    public String d3(User user) {
        System.out.println(user);
        return "test";
    }

    @RequestMapping("data/d4")
    public String d4(@RequestParam("username") String name, ModelMap modelMap) {
        modelMap.addAttribute("msg", name); // 相当于request.setAttribute("msg",name);
        System.out.println(name);
        return "test";
    }

    @RequestMapping("data/d5")
    public String d5(@RequestParam("username") String name, Model model) {
        model.addAttribute("msg", name); // 相当于request.setAttribute("msg",name);
        System.out.println(name);
        return "test";
    }
}
