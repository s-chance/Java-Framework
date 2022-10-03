package com.entropy.pojo;

import lombok.*;

@Data //自动生成Getter和Setter方法
@AllArgsConstructor //自动生成有参构造方法
@NoArgsConstructor //自动生成无参构造方法
@ToString //自动生成ToString方法
@EqualsAndHashCode //自动生成equals和hashCode方法
public class User {

    private Integer id;
    private String name;
    private String password;

}
