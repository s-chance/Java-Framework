package com.entropy.proxy.service.aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BeforeLog implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        // method: 目标对象要执行的方法
        // objects: 被调用方法的参数
        // o: 目标对象
        System.out.println("Before " + o.getClass().getName() + ": " + method.getName() + "...");
    }
}
