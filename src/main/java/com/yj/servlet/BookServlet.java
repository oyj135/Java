package com.yj.servlet;

import com.yj.bean.Book;
import com.yj.bean.Page;
import com.yj.service.BookService;
import com.yj.service.impl.BookServiceImpl;
import com.yj.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     BookServlet类
 */

public class BookServlet extends BaseServlet {

    /**
     * 声明BookService
     */
    BookService bookService = new BookServiceImpl();

    /**
     * 添加图书功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //书籍后显示到最后一页
        // 获取当前页码
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),0);
        // 当前页码 + 1
        pageNo+=1;

        //封装JavaBean
        Book book = (Book) WebUtils.copygetParameterMapToBean(req.getParameterMap(),new Book());

        //执行Service的添加书籍
        bookService.addBook(book);

        //跳转到图书列表页面
        ///manager/bookServlet?action=queryBookList
        /**
         * 此处只能用重定向
         * 用请求转发的话，由于浏览器URL中保存了请求参数，当用户刷新画面的时候，还会执行添加的请求。
         */
//        req.getRequestDispatcher("/manager/bookServlet?action=queryBookList").forward(req,resp);
        //没有分页功能
//        resp.sendRedirect(req.getContextPath() + "/manager/bookServlet?action=queryBookList");
        //分页功能
        resp.sendRedirect(req.getContextPath() + "/administer/bookServlet?action=page&pageNo=" + pageNo);
    }

    /**
     * 修改图书信息功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取pageNo
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);

        //封装JavaBean
        Book book = (Book) WebUtils.copygetParameterMapToBean(req.getParameterMap(),new Book());

        //调用service的更新处理
        bookService.updateBookById(book);

        //跳转到列表一览画面
        resp.sendRedirect(req.getContextPath() + "/administer/bookServlet?action=page&pageNo=" + pageNo);

    }

    /**
     * 删除图书功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取pageNo
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);

        //获取ID
        String id= req.getParameter("id");
        //调用service删除图书功能
        bookService.deleteBookById(Integer.parseInt(id));
        //响应重定向
        resp.sendRedirect(req.getContextPath() + "/administer/bookServlet?action=page&pageNo=" + pageNo);
    }

    /**
     * 查询指定ID的图书信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void queryBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取图书ID值
        int id = WebUtils.parseInt(req.getParameter("id"),1);

        //执行查询操作
        Book book = bookService.queryBookById(id);

        //回传数据
        req.setAttribute("book",book);

        //跳转到图书编辑页面(/pages / manager / book_edit.jsp)pages/manager/book_edit.jsp?action=queryBook&methord=update&id=${book.id}
        req.getRequestDispatcher("/pages/administer/book_edit.jsp").forward(req,resp);
    }

    /**
     * 查询所有图书信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void queryBookList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //调用service
        List<Book> list = bookService.queryBooks();
        //不为null
        if(list != null){
            //回传bookList
            req.setAttribute("bookList",list);
            //返回页面
            req.getRequestDispatcher("/pages/administer/book_manager.jsp").forward(req,resp);
        }
    }

    /**
     * 分页
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取pageNo和pageSize参数
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);

        //调用service
        Page<Book> page = bookService.page(pageNo,pageSize);

        //设置分页条的url地址
        page.setUrl("administer/bookServlet?action=page");

        //回传数据
        req.setAttribute("page",page);

        //返回页面
        req.getRequestDispatcher("/pages/administer/book_manager.jsp").forward(req,resp);

    }



}
