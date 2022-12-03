package com.entropy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ResponseController {

    @RequestMapping("/resp/r1")
    public void r1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("servlet api");
    }

    @RequestMapping("/resp/r2")
    public void r2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 重定向
        // 这里不能重定向WEB-INF目录下的文件
        response.sendRedirect("/springmvc_02_hello_war_exploded/index.jsp"); // tomcat配置的项目名/视图文件拓展名
    }

    @RequestMapping("/resp/r3")
    public void r3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 转发
        request.setAttribute("msg", "result/r3");
        request.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(request, response);
    }
}
