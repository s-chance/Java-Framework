package com.entropy.proxy.service.aop;

public class MyPointcut {
    public void before() {
        System.out.println("自定义切入: 方法执行前");
    }

    public void after() {
        System.out.println("自定义切入: 方法执行后");
    }
}
