package com.entropy.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        // 创建ModelAndView模型和视图
        ModelAndView modelAndView = new ModelAndView();
        // 封装对象到ModelAndView中
        modelAndView.addObject("msg", "Hello SpringMVC");
        // 封装要跳转的视图, 指定视图名称
        modelAndView.setViewName("hello"); // /WEB-INF/jsp/hello.jsp
        return modelAndView;
    }
}