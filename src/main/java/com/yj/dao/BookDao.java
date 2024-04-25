package com.yj.dao;

import com.yj.bean.Book;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     BookDao
 */

public interface BookDao {

    /**
     * 添加书籍信息
     * @param book
     * @return
     */
    int addBook(Book book);

    /**
     * 通过id删除书籍信息
     * @param id
     * @return
     */
    int deleteBookById(Integer id);

    /**
     * 更新书籍信息
     * @param book
     * @return
     */
    int updateBookById(Book book);

    /**
     * 通过Id查询书籍信息
     * @param id
     * @return
     */
    Book queryBookById(Integer id);

    /**
     * 查询所有书籍信息
     * @return
     */
    List<Book> queryBooks();

    /**
     * 查询t_book表有效的数据件数
     * @return 返回数据表的有效件数
     */
    int queryForRecordTotal();

    /**
     * 通过价格区间查询图书有效件数
     * @param minPrice
     * @param maxPrice
     * @return
     */
    int queryForRecordTotal(int minPrice, int maxPrice);

    /**
     * 查询当前页的数据
     * @param begin      开始数值
     * @param pageSize   每页容量
     * @return           当前页数据
     */
    List<Book> queryForItems(Integer begin, int pageSize);

    /**
     * 通过价格区间查询书籍有效信息
     * @param begin
     * @param pageSize
     * @param minPrice
     * @param maxPrice
     * @return
     */
    List<Book> queryForItems(int begin, int pageSize, int minPrice, int maxPrice);

}
