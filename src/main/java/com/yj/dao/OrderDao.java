package com.yj.dao;

import com.yj.bean.Order;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     订单接口
 */

public interface OrderDao {

    /**
     * 保存订单信息
     * @param order 订单信息
     * @return      保存结果
     */
    int saveOreder(Order order);

    /**
     *  查询当前用户的所有订单信息
     * @param userId 用户ID
     * @return       当前用户的所有订单信息
     */
    List<Order> queryOrdersByUserId(Integer userId);

    /**
     * 管理员查询全部订单
     * @return
     */
    List<Order> queryOrders();

    /**
     * 更新订单的状态
     * @param orderId   订单号
     * @param status    状态
     */
    void updateStatus(String orderId,Integer status);

}
