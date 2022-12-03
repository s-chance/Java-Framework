package com.entropy.service;

import com.entropy.dao.BookMapper;
import com.entropy.pojo.Book;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public int addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    @Override
    public int updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public Book queryById(int id) {
        return bookMapper.queryById(id);
    }

    @Override
    public List<Book> queryAll() {
        return bookMapper.queryAll();
    }

    @Override
    public List<Book> queryByName(String name) {
        return bookMapper.queryByName(name);
    }
}
