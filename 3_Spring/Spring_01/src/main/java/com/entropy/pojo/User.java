package com.entropy.pojo;

public class User {

    private String name;

    public User() {
        System.out.println("NoArgsConstructor...");
    }

    public User(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("Hello!This is " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
