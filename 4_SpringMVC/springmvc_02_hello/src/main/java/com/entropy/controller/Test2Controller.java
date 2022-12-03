package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test2Controller {
    // 实际地址: 项目名/t2
    @RequestMapping("/t2")
    public String test(Model model) {
        // 向模型中添加属性msg与值, 可以在JSP页面中取出并渲染
        model.addAttribute("msg", "test second");
        return "test"; // 返回给视图解析器, 最终得到/WEB-INF/jsp/test.jsp
    }
}
