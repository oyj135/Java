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

/**
 * @author 阳健
 * 概要：
 *     用于处理首页的
 */

public class ClientBookServlet extends BaseServlet {

    BookService bookService = new BookServiceImpl();

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

        //分页条url设定
        page.setUrl("client/clientBookServlet?action=page");

        //回传数据
        req.setAttribute("page",page);

        //返回页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);

    }

    /**
     * 价格区间分页
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void pageByPrice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取pageNo和pageSize参数
        int pageNo = WebUtils.parseInt(req.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(req.getParameter("pageSize"), Page.PAGE_SIZE);
        int minPrice = WebUtils.parseInt(req.getParameter("minPrice"),0);
        int maxPrice = WebUtils.parseInt(req.getParameter("maxPrice"),Integer.MAX_VALUE);

        //调用service
        Page<Book> page = bookService.page(pageNo,pageSize,minPrice,maxPrice);

        //分页条url设定
        /*
            当点击分页条的链接时，需要带上价格区间参数。目的是为了通过分页点击链接的时候，价格区间的值被保留。
         */

        StringBuilder sb= new StringBuilder("client/clientBookServlet?action=pageByPrice");

        //最小价格
        sb.append("&minPrice=" + minPrice);

        //最大价格
        sb.append("&maxPrice=" + maxPrice);

        page.setUrl(sb.toString());

        //回传数据
        req.setAttribute("page",page);

        //返回页面
        req.getRequestDispatcher("/pages/client/index.jsp").forward(req,resp);

    }
}
