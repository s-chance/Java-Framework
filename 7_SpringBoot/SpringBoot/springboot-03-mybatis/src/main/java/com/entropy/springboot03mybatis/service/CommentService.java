package com.entropy.springboot03mybatis.service;

import com.entropy.springboot03mybatis.pojo.Comment;
import com.github.pagehelper.PageInfo;

public interface CommentService {
    // 分页查询方法
    PageInfo<Comment> findAll(int pageNum, int pageSize);
}
