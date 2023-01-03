package com.entropy.springboot02jpa.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "t_comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "content")
    private String content;
    private String author;
    private Integer aId;
}
