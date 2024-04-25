package com.yj.dao;

import com.yj.bean.Book;
import com.yj.dao.BookDao;
import com.yj.dao.impl.BookDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author 阳健
 * 概要：
 *     BookDao测试类
 */

public class BookDaoTest {

    BookDao bookDao = new BookDaoImpl();

    @Test
    public void addBook() {
        Book book = new Book();
        book.setName("钢铁是怎样炼成的");
        book.setAuthor("雷夫托尔斯泰");
        book.setPrice(new BigDecimal(29.9));
        book.setSales(10000);
        book.setStock(200);
        book.setDelFlg("0");
        if(bookDao.addBook(book) != -1){
            System.out.println("插入数据成功");
        }else{
            System.out.println("插入数据失败");
        }

    }

    @Test
    public void deleteBookById() {
        if(bookDao.deleteBookById(12) != -1){
            System.out.println("删除数据成功");
        }else{
            System.out.println("删除数据失败");
        }
    }

    @Test
    public void updateBook() {

        Book book = new Book();
        book.setId(11);
        book.setName("钢铁是怎样炼成的2");
        book.setAuthor("雷夫托尔斯泰2");
        book.setPrice(new BigDecimal(39.9));
        book.setSales(10009);
        book.setStock(209);
        book.setDelFlg("0");
        book.setImgPath("static/img/0.jpg");
        book.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if(bookDao.updateBookById(book) != -1){
            System.out.println("更新数据成功");
        }else{
            System.out.println("更新数据失败");
        }
    }

    @Test
    public void queryBookById() {
        Book book = bookDao.queryBookById(11);
        if( book != null){

            System.out.println("查询数据成功");
            System.out.println("图书信息如下:" + book);
        }else{
            System.out.println("查询数据失败");
        }
    }

    @Test
    public void queryBooks() {
        List<Book> list = bookDao.queryBooks();

        if( list !=null ) {
            System.out.println("查询图书信息成功");
            list.forEach(new Consumer<Book>() {
                public void accept(Book book) {
                    System.out.println(book);
                }
            });
        }else{
            System.out.println("查询图书信息失败");
        }
    }
}