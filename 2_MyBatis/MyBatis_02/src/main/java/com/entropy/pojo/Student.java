package com.entropy.pojo;

import lombok.Data;

@Data
public class Student {
    private Integer id;
    private String name;

    private Teacher teacher;
}
