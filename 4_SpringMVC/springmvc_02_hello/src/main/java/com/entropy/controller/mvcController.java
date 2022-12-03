package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mvcController {

    // 测试以下方法需要注释掉springmvc-servlet.xml中的视图解析器配置, 否则会出现404错误
    @RequestMapping("/mvc/m1")
    public String m1(Model model) {
        model.addAttribute("msg", "mvc/m1");
        return "/WEB-INF/jsp/test.jsp"; // 转发
    }

    @RequestMapping("/mvc/m2")
    public String m2(Model model) {
        model.addAttribute("msg", "mvc/m2");
        return "forward:/WEB-INF/jsp/test.jsp"; // 转发, 另一种写法
    }

    @RequestMapping("/mvc/m3")
    public String m3(Model model) {
        model.addAttribute("msg", "mvc/m3");
        return "redirect:/index.jsp"; // 重定向
    }
}
