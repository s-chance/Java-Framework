package com.entropy.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.entropy.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class FastJsonTest {
    public static void main(String[] args) {
        List<User> list = new ArrayList<User>();

        User a = new User(1, "a", 11);
        User b = new User(2, "b", 12);
        User c = new User(3, "c", 13);
        User d = new User(4, "d", 14);

        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        // Java对象转换为Json字符串
        System.out.println("JavaObject To JsonString");
        String jsonString = JSON.toJSONString(list);
        System.out.println(list + " -> " + jsonString);
        String s = JSON.toJSONString(a);
        System.out.println(a + " -> " + s);
        System.out.println();
        // Json字符串转换为Java对象
        System.out.println("JsonString To JavaObject");
        User user = JSON.parseObject(s, User.class);
        System.out.println(s + " -> " + user);
        System.out.println();
        // Java对象转换为Json对象
        System.out.println("JavaObject To JsonObject");
        JSONObject jsonObject = (JSONObject) JSON.toJSON(user);
        System.out.println(user + " -> " + jsonObject);
        System.out.println();
        // Json对象转换为Java对象
        System.out.println("JsonObject To JavaObject");
        User javaObject = JSON.toJavaObject(jsonObject, User.class);
        System.out.println(jsonObject + " -> " + javaObject);
    }
}
