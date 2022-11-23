package com.entropy.proxy;

// 代理角色: 中间商
public class MyProxy implements Rent {

    // 真实角色委托代理角色去完成任务
    private Host host;

    public MyProxy() {
    }

    public MyProxy(Host host) {
        this.host = host;
    }

    @Override
    public void rent() {
        show();
        trade();
        pay();
    }

    public void show() {
        System.out.println("come and take a look");
    }

    public void trade() {
        System.out.println("buy it");
    }

    public void pay() {
        System.out.println("price: 120");
    }
}
