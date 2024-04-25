package com.yj.servlet;

import com.yj.bean.User;
import com.yj.service.AdminService;
import com.yj.service.impl.AdminServiceImpl;
import com.yj.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 概要:
 *
 * @author 阳健
 */
public class AdminServlet extends BaseServlet{
    /**
     * adminService
     */
    AdminService adminService = new AdminServiceImpl();

    /**
     * 管理员后台首页
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected  void adminHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if(user == null){
            //返回管理登录页面
            req.getRequestDispatcher("pages/administer/admin_login.jsp").forward(req, resp);
        }else{
            //返回后台管理页面
            req.getRequestDispatcher("pages/administer/manager.jsp").forward(req,resp);
        }

    }

    /**
     * 管理员登录功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void adminLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //使用BeanUtil进行封装
        User user = (User) WebUtils.copygetParameterMapToBean(req.getParameterMap(),new User());

        //获取复选框数据
        String remember = req.getParameter("remember");

        //调用service
        User dbUser = adminService.adminLogin(user);

        //用户和密码正确
        if(null != dbUser){

            //检查当前用户权限
            Boolean result = adminService.permissionCheck(dbUser);
            //用户不存在
            if(result == null){
                req.setAttribute("msg","该用户不存在");
                req.setAttribute("username",user.getUsername());

                //跳转到登录页面
                req.getRequestDispatcher("pages/administer/admin_login.jsp").forward(req,resp);
            }else{
                //用户没有权限
                if(!result){
                    //回传信息
                    req.setAttribute("msg","该用户没有管理权限");
                    req.setAttribute("username",user.getUsername());

                    //跳转到登录页面
                    req.getRequestDispatcher("pages/administer/admin_login.jsp").forward(req,resp);
                //用户拥有权限
                }else{
                    //判断用户是否勾选记住我
                    if ("6".equals(remember)){
                        //勾选了，发送cookie
                        Cookie username = new Cookie("username_admin",user.getUsername());
                        Cookie password = new Cookie("password_admin",user.getPassword());

                        //设置cookie存活时间
                        username.setMaxAge(60*60*24);
                        password.setMaxAge(60*60*24);

                        //发送
                        resp.addCookie(username);
                        resp.addCookie(password);
                    }
                    //user信息
                    //登录成功的信息存放到session
                    req.getSession().setAttribute("user", dbUser);

                    //跳转到登录成功页面
                    req.getRequestDispatcher("pages/administer/manager.jsp").forward(req, resp);
                }
            }

        }else{
            //回传信息
            req.setAttribute("msg","用户名或者密码错误！");
            req.setAttribute("username",user.getUsername());

            //跳转到登录页面
            req.getRequestDispatcher("pages/administer/admin_login.jsp").forward(req,resp);
        }
    }

    /**
     * 退出登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //销毁session
        req.getSession().invalidate();

        //跳转到登录页面
        resp.sendRedirect(req.getContextPath() + "/index.jsp");

    }

    /**
     * 查询所有的管理员
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void queryManagerList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //权限类型取得
        String permissionType = req.getParameter("permissionType");

        //调用service
        List<User> list = adminService.queryManagerByPermissionType(permissionType);
        //不为null
        if(list != null){
            //回传userList
            req.setAttribute("userList",list);
            req.getSession().setAttribute("permissionType",permissionType);
            //返回页面
            req.getRequestDispatcher("/pages/administer/manager_manager.jsp").forward(req,resp);
        }
    }

    /**
     * 查询单个管理员
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void queryManagerOne(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户名
        String userName = req.getParameter("username");

        User user = adminService.queryManagerByUserName(userName);

        //不为null
        if(user != null){
            //回传userList
            req.setAttribute("user",user);
            //返回页面
            req.getRequestDispatcher("/pages/administer/manager_edit.jsp").forward(req,resp);
        }
    }

    /**
     * 更新管理员信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //参数转换为bean
        User user = (User) WebUtils.copygetParameterMapToBean(req.getParameterMap(),new User());
        String permissionType = (String) req.getSession().getAttribute("permissionType");

        //不为null
        if(user != null){
            //更新DB
            adminService.updateManagerByUserName(user);
            //回传userList
            req.setAttribute("user",user);

            //转发URL中含有中文乱码解决
            String encodePermissionType = URLEncoder.encode(permissionType,"UTF-8");
            //返回页面
            resp.sendRedirect(req.getContextPath() + "/adminServlet?action=queryManagerList&permissionType=" + encodePermissionType);
        }
    }

    /**
     * 删除指定管理员
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //参数转换为bean
        User user = (User) WebUtils.copygetParameterMapToBean(req.getParameterMap(),new User());
        String permissionType = (String) req.getSession().getAttribute("permissionType");

        //不为null
        if(user != null){
            //更新DB
            adminService.deleteManagerByUserName(user.getUsername());
            //回传userList
            req.setAttribute("user",user);

            //转发URL中含有中文乱码解决
            String encodePermissionType = URLEncoder.encode(permissionType,"UTF-8");
            //返回页面
            resp.sendRedirect(req.getContextPath() + "/adminServlet?action=queryManagerList&permissionType=" + encodePermissionType);
        }
    }

    protected void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //参数转换为bean
        User user = (User) WebUtils.copygetParameterMapToBean(req.getParameterMap(),new User());
        String permissionType = (String) req.getSession().getAttribute("permissionType");

        //不为null
        if(user != null){
            //更新DB
            adminService.addManager(user,permissionType);
            //回传userList
            req.setAttribute("user",user);

            //转发URL中含有中文乱码解决
            String encodePermissionType = URLEncoder.encode(permissionType,"UTF-8");
            //返回页面
            resp.sendRedirect(req.getContextPath() + "/adminServlet?action=queryManagerList&permissionType=" + encodePermissionType);
        }
    }


}
