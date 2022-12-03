package com.entropy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    // 这里的成员变量与数据库中的字段一一对应
    private Integer id;
    private String name;
    private Integer counts;
    private String detail;
    private BigDecimal price;
}
