package com.entropy.springboot03mybatis.dao;

import com.entropy.springboot03mybatis.pojo.Comment;

import java.util.List;

public interface CommentXmlMapper {
    List<Comment> findAll();
}
