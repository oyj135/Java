package com.yj.dao.impl;

import com.yj.bean.Book;
import com.yj.dao.BookDao;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     图书实现类
 */

public class BookDaoImpl extends BaseDao implements BookDao {

    /**
     * 添加书籍信息
     *
     * @param book
     * @return
     */
    @Override
    public int addBook(Book book) {
        String sql = "insert into t_book(name,author,price,sales,stock,image_path) values (?,?,?,?,?,?)";
        return update(sql,book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getImgPath());
    }

    /**
     * 通过id删除书籍信息
     *
     * @param id
     * @return
     */
    @Override
    public int deleteBookById(Integer id) {
        String sql = "update t_book set delflg = '1',updatetime = now() where id = ?";
        return update(sql,id);
    }

    /**
     * 更新书籍信息
     *
     * @param book
     * @return
     */
    @Override
    public int updateBookById(Book book) {
        String sql = "update t_book set name = ?,author = ?,price = ?,sales = ?,stock = ?,updatetime = now() where id = ?";
        return update(sql,book.getName(),book.getAuthor(),book.getPrice(),book.getSales(),book.getStock(),book.getId());
    }

    /**
     * 通过Id查询书籍信息
     *
     * @param id
     * @return
     */
    @Override
    public Book queryBookById(Integer id) {
        String sql = "select id,name,author,price,sales,stock,image_path imgPath,delflg,createtime,updatetime from t_book where id = ?";
        return queryForOne(Book.class,sql,id);
    }

    /**
     * 查询所有书籍信息
     *
     * @return
     */
    @Override
    public List<Book> queryBooks() {
        String sql = "select id,name,author,price,sales,stock,image_path imgPath,delflg,createtime,updatetime from t_book where delflg <> '1'";
        return queryForList(Book.class,sql);
    }

    /**
     * 查询t_book表有效的数据件数
     *
     * @return 返回数据表的有效件数
     */
    @Override
    public int queryForRecordTotal() {
        String sql = "select count(1) from t_book where delflg = '0' ";
        return ((Long)queryForSingleValue(sql)).intValue();
    }

    /**
     * 通过价格区间查询图书有效件数
     *
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public int queryForRecordTotal(int minPrice, int maxPrice) {
        String sql = "select count(1) from t_book where delflg = '0' and (price between ? and ?)";
        return ((Long)queryForSingleValue(sql,minPrice,maxPrice)).intValue();
    }

    /**
     * 查询当前页的数据
     * @param begin    开始数值
     * @param pageSize 每页容量
     * @return 当前页数据
     */
    @Override
    public List<Book> queryForItems(Integer begin, int pageSize) {
        String sql = "select id,name,author,price,sales,stock,image_path imgPath,delflg,createtime,updatetime from t_book where delflg <> '1' limit ?,?";
        return queryForList(Book.class,sql,begin,pageSize);
    }

    /**
     * 通过价格区间查询书籍有效信息并按照价格升序
     *
     * @param begin
     * @param pageSize
     * @param minPrice
     * @param maxPrice
     * @return
     */
    @Override
    public List<Book> queryForItems(int begin, int pageSize, int minPrice, int maxPrice) {
        String sql = "select id,name,author,price,sales,stock,image_path imgPath,delflg,createtime,updatetime from t_book where delflg <> '1' and (price between ? and ?) order by price limit ?,?";
        return queryForList(Book.class,sql,minPrice,maxPrice,begin,pageSize);
    }

}
