package com.yj.dao;

import com.yj.bean.OrderItem;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     订单项接口
 */

public interface OrderItemDao {
    /**
     *  保存订单项信息
     * @param orderItem   订单项信息
     * @return
     */
    int saveOrderItem(OrderItem orderItem);

    /**
     *  通过订单号查询订单的详细商品信息
     * @param orderId    订单号
     * @return           订单的商品信息
     */
    List<OrderItem> queryOderItemByOrderId(String orderId);

}
