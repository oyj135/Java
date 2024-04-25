package com.yj.filter;

import com.yj.bean.Role;
import com.yj.bean.User;
import com.yj.constants.Constants;
import com.yj.dao.RoleDao;
import com.yj.dao.impl.RoleDaoImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 阳健
 * 概要：
 *    实现Manager
 */

public class ManagerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("ManagerFilter初期化完成！");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //转换类型
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        //获取session的user
        User user = (User) httpServletRequest.getSession().getAttribute("user");

        //用户没有登录
        if(user == null){
            //管理员登录页面
            httpServletRequest.getRequestDispatcher("/pages/administer/admin_login.jsp").forward(request,response);
        }else{
            //用户登录后台管理的权限检查
            RoleDao roleDao = new RoleDaoImpl();
            Role role = roleDao.queryRoleByUserName(user.getUsername());
            if(role != null ) {
                //如果角色为管理员或者根角色，则登录
                if (Constants.CONST_ROLE_ROOT.equals(role.getRoleName()) ||
                        Constants.CONST_ROLE_MANAGER.equals(role.getRoleName())) {
                    chain.doFilter(request,response);
                } else {
                    httpServletRequest.getRequestDispatcher("/pages/administer/admin_login.jsp").forward(request,response);
                }
            }else {
                httpServletRequest.getRequestDispatcher("/pages/error/error_500.jsp").forward(request,response);
            }
        }


    }

    @Override
    public void destroy() {

    }
}
