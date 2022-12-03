package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestfulController {
    // 传统风格
    @RequestMapping("/add") // 访问路径: 项目名/add?a=1&b=2, 参数名需对应匹配
    public String add(int a, int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", res);
        return "test";
    }

    // REST风格
    @RequestMapping("/add/{p1}/{p2}") // 访问路径: 项目名/add/1/2
    public String restfulAdd(@PathVariable int p1, @PathVariable int p2, Model model) {
        int res = p1 + p2;
        //Spring MVC会自动实例化一个Model对象用于向视图中传值
        model.addAttribute("msg", "restful " + res);
        //返回视图位置
        return "test";
    }

    // 直接在浏览器地址栏上访问会出现405错误, 因为浏览器地址栏默认为GET请求
    @RequestMapping(value = "/post", method = RequestMethod.POST) // 只匹配POST请求
    public String post(Model model) {
        model.addAttribute("msg", "this is a post request");
        return "test";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET) // 只匹配GET请求
    public String get(Model model) {
        model.addAttribute("msg", "this is a get request");
        return "test";
    }
}
