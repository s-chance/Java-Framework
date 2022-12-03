package com.entropy.controller;

import com.entropy.pojo.Book;
import com.entropy.service.BookService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;

    // 查询所有书籍
    @RequestMapping("/all")
    public String all(Model model) {
        List<Book> books = bookService.queryAll();
        model.addAttribute("all", books);
        return "allBooks";
    }

    // 跳转到添加书籍页面
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "addBook";
    }

    // 添加书籍
    @RequestMapping("/add")
    public String add(Book book) {
        System.out.println("add " + book);
        bookService.addBook(book);
        // 重定向到书籍列表页面
        return "redirect:/book/all";
    }

    // 跳转到修改页面, 携带id数据用于修改指定书籍信息
    @RequestMapping("/toUpdate")
    public String toUpdate(int id, Model model) {
        Book book = bookService.queryById(id);
        model.addAttribute("book", book);
        return "updateBook";
    }

    // 修改书籍信息
    @RequestMapping("/update")
    public String update(Book book, Model model) {
        System.out.println("update " + book);
        bookService.updateBook(book);
        // 更新书籍后的信息
        Book queryById = bookService.queryById(book.getId());
        System.out.println("after update " + queryById);
        return "redirect:/book/all";
    }

    // 删除书籍
    @RequestMapping("/delete/{bookId}")
    public String delete(@PathVariable("bookId") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/all";
    }

    // 根据书名查询
    @RequestMapping("/queryByName")
    public String queryByName(String name, Model model) {
        List<Book> books = bookService.queryByName(name);
        if (books.size() == 0) {
            model.addAttribute("error", "未找到任何信息");
        } else {
            model.addAttribute("all", books);
        }
        System.out.println(books);
        return "allBooks";
    }
}
