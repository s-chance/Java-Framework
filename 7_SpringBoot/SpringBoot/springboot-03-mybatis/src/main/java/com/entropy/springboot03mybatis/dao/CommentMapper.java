package com.entropy.springboot03mybatis.dao;

import com.entropy.springboot03mybatis.pojo.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    @Select("select * from t_comment")
    List<Comment> findAll();
}
