package com.entropy.proxy;

import com.entropy.proxy.service.ObjectProxyInvocationHandler;
import com.entropy.proxy.service.UserService;
import com.entropy.proxy.service.UserServiceImpl;
import com.entropy.proxy.service.UserServiceProxy;
import org.junit.Test;

// 客户: 租客
public class Client {
    public static void main(String[] args) {

        Host host = new Host();
//        host.rent(); // 如果不找代理就是直接从房东这边交易
        MyProxy proxy = new MyProxy(host); // 如果房东选择代理, 则由代理人(中间商)来接待租客
        proxy.rent();
        System.out.println();

//        UserService userService = new UserServiceImpl();
//        userService.add();
        UserServiceImpl userService = new UserServiceImpl();
        UserServiceProxy userServiceProxy = new UserServiceProxy();
        userServiceProxy.setUserService(userService);
        userServiceProxy.add();
    }

    @Test
    public void test() {
        Host host = new Host();
        // 实例化代理实例的处理类, 即动态代理类
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        // 设置真实角色
        proxyInvocationHandler.setRent(host);
        // 动态生成代理类
        Rent proxy = (Rent) proxyInvocationHandler.getProxy();
        proxy.rent();
        System.out.println();

        UserServiceImpl userService = new UserServiceImpl();
        ObjectProxyInvocationHandler objectProxyInvocationHandler = new ObjectProxyInvocationHandler();
        objectProxyInvocationHandler.setTarget(userService);
        UserService service = (UserService) objectProxyInvocationHandler.getProxy();
        service.add();
    }
}
