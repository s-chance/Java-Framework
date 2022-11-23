package com.entropy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {

    private Rent rent; // 基于接口实现代理

    public void setRent(Rent rent) {
        this.rent = rent;
    }

    // 生成代理类
    public Object getProxy() {
        // 加载此类时生成Rent接口的代理类, 由于代理的是接口, 因此可以代理一类角色而不是单个角色
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), rent.getClass().getInterfaces(), this);
    }

    // 处理代理实例上的方法调用并返回结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 参数说明 proxy: 被代理的类  method: 用于调用代理类的处理方法实现代理
        // 核心机制是反射
        show();
        Object invoke = method.invoke(rent, args);
        pay();
        return invoke;
    }

    public void show() {
        System.out.println("show a big house");
    }

    public void pay() {
        System.out.println("pay a lot of money");
    }
}
