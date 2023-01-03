package com.entropy.springboot02jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity // 实体类注解
public class User {
    @Id
    @GeneratedValue //自动生成Id
    private Long id;

    private String name;
}
