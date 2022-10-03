package com.entropy.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Blog implements Serializable {
    private String id;
    private String title;
    private String author;
    private Date createTime; //属性名与字段名不一致, 开启驼峰命名自动映射
    private Integer views;
}
