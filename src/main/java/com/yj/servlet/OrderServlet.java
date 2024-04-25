package com.yj.servlet;

import com.yj.bean.Cart;
import com.yj.bean.Order;
import com.yj.bean.OrderItem;
import com.yj.bean.User;
import com.yj.service.OrderService;
import com.yj.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author 阳健
 * 概要：
 *    OrderServlet
 */

public class OrderServlet extends BaseServlet{

    /**
     * 订单service声明
     */
    OrderService orderService = new OrderServiceImpl();

    /**
     * 创建订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取参数
        //获取购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        //获取用户信息
        User user = (User) req.getSession().getAttribute("user");

        if(user == null){
            //跳转到的登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
            //转发和重定向后不需要执行任何其他操作
            return;
        }

        //调用service
        if(cart != null){

            String orderId = orderService.createOrder(cart, user.getId());

            //回传数据
            req.getSession().setAttribute("orderId",orderId);

            /*
             返回页面
             这个地方要使用重定向 否则在用户刷新的时候又会生成一个订单
             */
//            resp.sendRedirect(req.getContextPath() + "/pages/cart/checkout.jsp");
            //跳转到支付页面
            resp.sendRedirect(req.getContextPath() + "/pages/pay/pay.jsp");
        }

    }

    /**
     * 查看我的所有订单信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取参数 用户ID
        User user = (User) req.getSession().getAttribute("user");

        //登录的情况
        if(user != null) {
            //查看
            List<Order> orderList = orderService.showMyOrders(user.getId());

            //回传数据
            req.setAttribute("orderList", orderList);

            //返回页面
            req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);


        }else{
            //未登录的情况下
            //返回页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req, resp);
        }

    }

    /**
     * 查看该订单详情
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取参数 订单号
        String orderId = req.getParameter("orderId");

        //查看
        List<OrderItem> orderItemList = orderService.showOrderDetail(orderId);

        //回传数据
        req.setAttribute("orderItemList", orderItemList);

        //返回页面
        req.getRequestDispatcher("/pages/order/order_detail.jsp").forward(req, resp);


    }

    /**
     * 管理员查看所有订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //查看
        List<Order> allOrder = orderService.showAllOrders();

        //回传数据
        req.setAttribute("allOrder", allOrder);

        //返回页面
        req.getRequestDispatcher("/pages/administer/order_manager.jsp").forward(req, resp);

    }

    /**
     * 发货
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void sendOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取订单号
        String orderId = req.getParameter("orderId");

        //发货
        orderService.sendOrder(orderId);

        //查新最新数据
        List<Order> allOrder = orderService.showAllOrders();

        //回传数据
        req.setAttribute("allOrder", allOrder);

        //返回页面
        req.getRequestDispatcher("/pages/administer/order_manager.jsp").forward(req, resp);

    }

    /**
     * 签收订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void signOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取订单号
        String orderId = req.getParameter("orderId");

        //获取用户
        User user= (User) req.getSession().getAttribute("user");

        //签收订单
        orderService.signOrder(orderId);

        //查看
        List<Order> orderList = orderService.showMyOrders(user.getId());

        //回传数据
        req.setAttribute("orderList", orderList);

        //返回页面
        req.getRequestDispatcher("/pages/order/order.jsp").forward(req, resp);

    }

    /**
     * 支付
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void payOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取订单号
        String orderId = req.getParameter("orderId");

        //支付
        orderService.payOrder(orderId);

        //获取购物车
        Cart cart = (Cart) req.getSession().getAttribute("cart");

        if(cart != null) {
            //支付完的东西清空购物车
            req.getSession().removeAttribute("cart");
        }

        //回传数据
        req.getSession().setAttribute("orderId", orderId);

        //返回页面
        req.getRequestDispatcher("/pages/pay/checkout.jsp").forward(req, resp);

    }

}
