package com.entropy.utils;

import org.junit.Test;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Test
    public void test() {
        //每次获得的UUID都是全局唯一的, 适用于分布式系统设计
        System.out.println(UUIDUtils.getUUID());
        System.out.println(UUIDUtils.getUUID());
    }
}
