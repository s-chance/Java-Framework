package com.entropy.service;

import com.entropy.pojo.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookService {
    // 增加Book
    int addBook(Book book);
    // 根据ID删除Book
    int deleteBookById(int id); // @Param注解用于命名SQL参数
    // 更新Book
    int updateBook(Book book);
    // 根据ID查询Book
    Book queryById(int id);
    // 查询所有Book
    List<Book> queryAll();
    // 根据name查询Book
    List<Book> queryByName(String name);
}
