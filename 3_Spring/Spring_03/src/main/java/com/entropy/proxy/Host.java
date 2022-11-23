package com.entropy.proxy;

// 真实角色: 房东
public class Host implements Rent {
    @Override
    public void rent() {
        System.out.println("price: 100");
    }
}
