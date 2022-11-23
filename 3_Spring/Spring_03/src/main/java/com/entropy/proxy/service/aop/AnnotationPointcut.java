package com.entropy.proxy.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect // 通过@Aspect注解标记该类为切面
public class AnnotationPointcut {
    @Before("execution(* com.entropy.proxy.service.UserServiceImpl.*(..))")
    public void before() {
        System.out.println("注解式aop: 方法执行前");
    }

    @After("execution(* com.entropy.proxy.service.UserServiceImpl.*(..))")
    public void after() {
        System.out.println("注解式aop: 方法执行后");
    }

    @Around("execution(* com.entropy.proxy.service.UserServiceImpl.*(..))")
    public void around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕前");
        // 获取签名
        System.out.println(joinPoint.getSignature());
        // 执行目标方法
        Object proceed = joinPoint.proceed();
        System.out.println("环绕后");
        System.out.println(proceed);
    }
}
