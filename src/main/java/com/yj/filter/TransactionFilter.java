package com.yj.filter;

import com.yj.utils.JdbcUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author 阳健
 * 概要：
 *     事务管理过滤器
 */

public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //对事务进行管理
        try {
            chain.doFilter(request, response);
            //提交事务并关闭连接
            JdbcUtils.commitAndClose();
        }catch (Exception e){
            //回滚事务并关闭连接
            JdbcUtils.rollbackAndClose();
            //打印异常
            e.printStackTrace();
            //把异常抛给tomacat服务器(然后服务器进行捕捉后，匹配对应的web.xml错误画面)
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TransactionFilter 初期化完成！");
    }

    @Override
    public void destroy() {

    }
}
