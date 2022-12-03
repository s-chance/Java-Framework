package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EncodeController {

    @RequestMapping("/encode")
    public String encode(Model model, String name) {
        model.addAttribute("msg", name); // 存放表单提交的数据
        return "test";
    }
}
