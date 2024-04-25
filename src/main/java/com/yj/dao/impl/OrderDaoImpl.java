package com.yj.dao.impl;

import com.yj.bean.Order;
import com.yj.dao.OrderDao;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     订单Dao的实现类
 */

public class OrderDaoImpl extends BaseDao implements OrderDao {
    /**
     * 保存订单信息
     *
     * @param order 订单信息
     */
    @Override
    public int saveOreder(Order order) {
        String sql = "insert into t_order(orderid, createtime, price, status, userid) values (?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    /**
     * 查询当前用户的所有订单信息
     *
     * @param userId 用户ID
     * @return 当前用户的所有订单信息
     */
    @Override
    public List<Order> queryOrdersByUserId(Integer userId) {
        String sql = "select orderid,createtime,price,status,userid from t_order where userid = ? order by createtime";
        return queryForList(Order.class,sql,userId);
    }

    /**
     * 管理员查询全部订单
     *
     * @return
     */
    @Override
    public List<Order> queryOrders() {
        String sql = "select orderid,createtime,price,status,userid from t_order";
        return queryForList(Order.class,sql);
    }

    /**
     * 更新订单的状态
     *
     * @param orderId 订单号
     * @param status  状态
     */
    @Override
    public void updateStatus(String orderId, Integer status) {
        String sql="update t_order set status = ? where orderId = ?; ";
        update(sql,status,orderId);
    }
}
