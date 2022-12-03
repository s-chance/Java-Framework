package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mvc2Controller {

    @RequestMapping("/mvc2/m1")
    public String m1(Model model) {
        model.addAttribute("msg", "mvc2/m1");
        return "test";
    }

    @RequestMapping("/mvc2/m2")
    public String m2(Model model) {
        model.addAttribute("msg", "mvc2/m2");
        return "redirect:/index.jsp";
    }

    @RequestMapping("mvc2/m3")
    public String m3() {
        return "redirect:/mvc2/m1"; // 这里的重定向指向了另外一个请求
    }
}
