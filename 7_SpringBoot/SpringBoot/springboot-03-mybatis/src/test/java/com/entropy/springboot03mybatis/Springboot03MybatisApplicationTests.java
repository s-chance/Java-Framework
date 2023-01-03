package com.entropy.springboot03mybatis;

import com.entropy.springboot03mybatis.dao.CommentMapper;
import com.entropy.springboot03mybatis.dao.CommentXmlMapper;
import com.entropy.springboot03mybatis.pojo.Comment;
import com.entropy.springboot03mybatis.service.CommentService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Springboot03MybatisApplicationTests {

    @Autowired
    private CommentMapper commentMapper;
    @Test
    void contextLoads() {
        List<Comment> comments = commentMapper.findAll();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Autowired
    private CommentXmlMapper commentXmlMapper;
    @Test
    public void test() {
        List<Comment> comments = commentXmlMapper.findAll();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }

    @Autowired
    private CommentService commentService;
    @Test
    public void testPage() {
        // 查询第二页, 每页3条数据
        PageInfo<Comment> info = commentService.findAll(2, 3);
        List<Comment> comments = info.getList();
        for (Comment comment : comments) {
            System.out.println(comment);
        }
    }
}
