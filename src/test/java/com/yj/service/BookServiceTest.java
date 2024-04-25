package com.yj.service;

import com.yj.bean.Book;
import com.yj.service.BookService;
import com.yj.service.impl.BookServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 阳健
 * 概要：
 *    BookService接口测试
 */

public class BookServiceTest {

    BookService bookService = new BookServiceImpl();

    @Test
    public void addBook() {
        Book book = new Book();
        book.setName("你幸福了吗？");
        book.setAuthor("白岩松");
        book.setPrice(new BigDecimal(39.9));
        book.setSales(200);
        book.setStock(100);
        book.setDelFlg("0");
        book.setImgPath("static/img/1.jpg");
        bookService.addBook(book);
    }

    @Test
    public void deleteBookById() {
        bookService.deleteBookById(10);
    }

    @Test
    public void updateBook() {
        Book book = new Book();
        book.setId(12);
        book.setName("你幸福了吗2？");
        book.setAuthor("白岩松");
        book.setPrice(new BigDecimal(49.9));
        book.setSales(2800);
        book.setStock(10);
        book.setDelFlg("0");
        book.setImgPath("static/img/1.jpg");
        bookService.updateBookById(book);
    }

    @Test
    public void queryBookById() {
        System.out.println(bookService.queryBookById(12));
    }

    @Test
    public void queryBooks() {
        List<Book> list = bookService.queryBooks();
        //使用lambda表达式遍历List
        list.forEach(book -> {
            System.out.println(book);
        });
    }
}