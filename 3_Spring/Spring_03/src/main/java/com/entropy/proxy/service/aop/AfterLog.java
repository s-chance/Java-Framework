package com.entropy.proxy.service.aop;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterLog implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        // o: 返回值
        // method: 被调用的方法
        // objects: 被调用方法的参数
        // o1: 目标对象
        System.out.println("After " + o1.getClass().getName() + ": " + method.getName() + " return " + o);
    }
}
