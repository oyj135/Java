package com.yj.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author 阳健
 * 概要：
 *     定义BaseServlet
 *         对多个模块使用相同的部分进行提取，做成BaseServlet类
 */

public class BaseServlet extends HttpServlet {
    /**
     * 对get请求进行action的获取和业务的分发。
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    /**
     * 对post请求进行action的获取和业务的分发。
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取action类型
        String action = req.getParameter("action");

        //解决响应的中文乱码
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");

        //使用反射进行业务的分离
        try {
            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,req,resp);

        } catch (Exception e) {
            e.printStackTrace();
            //继续往外抛异常让Filter的事务管理器进行捕捉
            throw new RuntimeException(e);
        }
    }

}
