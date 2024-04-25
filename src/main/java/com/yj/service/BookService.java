package com.yj.service;

import com.yj.bean.Book;
import com.yj.bean.Page;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *    BookService接口
 */

public interface BookService {
    /**
     * 添加图书
     * @param book 书籍信息
     */
    void addBook(Book book);

    /**
     * 通过ID删除图书
     * @param id 图书ID
     */
    void deleteBookById(Integer id);

    /**
     * 更新图书信息
     * @param book 书籍信息
     */
    void updateBookById(Book book);

    /**
     * 通过Id查询图书信息
     * @param id 图书Id
     * @return 图书信息
     */
    Book queryBookById(Integer id);

    /**
     * 查询所有图书信息
     * @return 所有图书信息List
     */
    List<Book> queryBooks();

    /**
     * 实现分页
     * @param pageNo     当前页码
     * @param pageSize   每页容纳数据条数
     * @return           分页模型对象
     */
    Page<Book> page(int pageNo, int pageSize);

    /**
     * 分页实现
     * @param pageNo    当前页码
     * @param pageSize  每页最大件数
     * @param minPrice  最小价格
     * @param maxPrice  最大价格
     * @return          分页模型对象
     */
    Page<Book> page(int pageNo, int pageSize, int minPrice, int maxPrice);

}
