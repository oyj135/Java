package com.yj.service.impl;

import com.yj.bean.Book;
import com.yj.bean.Page;
import com.yj.dao.BookDao;
import com.yj.dao.impl.BookDaoImpl;
import com.yj.service.BookService;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     BookService实现类
 */

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    /**
     * 添加图书
     *
     * @param book 书籍信息
     */
    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    /**
     * 通过ID删除图书
     *
     * @param id 图书ID
     */
    @Override
    public void deleteBookById(Integer id) {
        bookDao.deleteBookById(id);
    }

    /**
     * 更新图书信息
     *
     * @param book 书籍信息
     */
    @Override
    public void updateBookById(Book book) {
        if(book.getId() != null) {
            bookDao.updateBookById(book);
        }else{
            System.out.println("更新书籍信息的ID不能为NULL！");
        }
    }

    /**
     * 通过Id查询图书信息
     *
     * @param id 图书Id
     * @return 图书信息
     */
    @Override
    public Book queryBookById(Integer id) {
        return bookDao.queryBookById(id);
    }

    /**
     * 查询所有图书信息
     *
     * @return 所有图书信息List
     */
    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    /**
     * 实现分页
     *
     * @param pageNo   当前页码
     * @param pageSize 每页容纳数据条数
     * @return         返回分页信息对象
     */
    @Override
    public Page<Book> page(int pageNo, int pageSize) {
        //创建Page对象
        Page<Book> page = new Page<Book>();

        //总记录数
        int recordTotal = bookDao.queryForRecordTotal();
        page.setRecordTotal(recordTotal);

        //总页数
        int pageTotal = (recordTotal / pageSize);
        if(recordTotal % pageSize > 0){
            pageTotal += 1;
        }
        page.setPageTotal(pageTotal);

        //数据边界值验证
        if(pageNo < 1){
            pageNo = 1;
        }
        if(pageNo > pageTotal){
            pageNo = pageTotal;
        }

        //当前页码
        page.setPageNo(pageNo);

        //数据边界值验证
        if(pageSize < 1){
            pageSize = 1;
        }
        if(pageSize > recordTotal){
            pageSize = recordTotal;
        }
        //每页容量
        page.setPageSize(pageSize);

        //当前记录数据
        int begin =(pageNo - 1) * pageSize;
        List<Book> items = bookDao.queryForItems(begin,pageSize);
        page.setItems(items);

        return page;
    }

    /**
     * 通过价格区间实现分页
     *
     * @param pageNo   当前页码
     * @param pageSize 每页最大件数
     * @param minPrice 最小价格
     * @param maxPrice 最大价格
     */
    @Override
    public Page<Book> page(int pageNo, int pageSize, int minPrice, int maxPrice) {
        //创建Page对象
        Page<Book> page = new Page<Book>();

        //总记录数
        int recordTotal = bookDao.queryForRecordTotal(minPrice,maxPrice);
        page.setRecordTotal(recordTotal);

        //总页数
        int pageTotal = (recordTotal / pageSize);
        if(recordTotal % pageSize > 0){
            pageTotal += 1;
        }
        page.setPageTotal(pageTotal);

        //数据边界值验证
        if(pageNo < 1){
            pageNo = 1;
        }
        if(pageNo > pageTotal){
            pageNo = pageTotal;
        }

        //当前页码
        page.setPageNo(pageNo);

        //数据边界值验证
        if(pageSize < 1){
            pageSize = 1;
        }
        if(pageSize > recordTotal){
            pageSize = recordTotal;
        }
        //每页容量
        page.setPageSize(pageSize);

        //当前记录数据
        int begin =(pageNo - 1) * pageSize;
        List<Book> items = bookDao.queryForItems(begin,pageSize,minPrice,maxPrice);
        page.setItems(items);

        return page;
    }
}
