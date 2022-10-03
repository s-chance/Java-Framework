package com.entropy.dao;

import com.entropy.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    //新增数据
    int addBlog(Blog blog);

    //查询博客
    List<Blog> getBlogByIf(Map map);
    List<Blog> getBlogByChoose(Map map);

    //更新博客
    int updateBlog(Map map);

    //遍历查询博客
    List<Blog> getBlogByForeach(Map map);
}
