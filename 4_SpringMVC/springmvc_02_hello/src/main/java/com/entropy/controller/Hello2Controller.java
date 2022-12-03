package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 注册到Spring中
@RequestMapping("/hello")
// 实际地址: 项目名/hello/h1(下面有@RequestMapping)
// 实际地址: 项目名/hello(下面没有@RequestMapping)
public class Hello2Controller {
    // 实际地址: 项目名/hello/h1(上面有@RequestMapping)
    // 实际地址: 项目名/h1(上面没有@RequestMapping)
    @RequestMapping("/h1")
    public String test(Model model) {
        // 向模型中添加属性msg与值, 可以在JSP页面中取出并渲染
        model.addAttribute("msg", "annotation springmvc");
        return "hello"; // 返回给视图解析器, 最终得到/WEB-INF/jsp/hello.jsp
    }
}