package com.entropy.springboot03mybatis.service;

import com.entropy.springboot03mybatis.dao.CommentMapper;
import com.entropy.springboot03mybatis.pojo.Comment;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageInfo<Comment> findAll(int pageNum, int pageSize) {
        // 使用PageHelper指定当前页和每页条数
        PageHelper.startPage(pageNum, pageSize);
        // 查询数据
        List<Comment> all = commentMapper.findAll();
        // 把查询结果封装到PageInfo中
        PageInfo<Comment> pageInfo = new PageInfo(all);
        return pageInfo;
    }
}
