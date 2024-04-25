package com.yj.dao;

import com.yj.bean.OrderItem;
import com.yj.dao.OrderItemDao;
import com.yj.dao.impl.OrderItemDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 阳健
 * 概要：
 *
 */

public class OrderItemDaoTest {

    OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Test
    public void saveOrderItem() {

        //创建订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setGoodsName("C++入门");
        orderItem.setGoodsCount(2);
        orderItem.setPrice(new BigDecimal(50));
        orderItem.setTotalPrice(new BigDecimal(100));
        orderItem.setOrderId("1000-20201124160000-00002");

        OrderItemDao orderItemDao = new OrderItemDaoImpl();
        orderItemDao.saveOrderItem(orderItem);

    }

    /**
     * 通过订单号查询订单的商品新
     */
    @Test
    public void queryOderItemByOrderId() {
        String orderId= "16062948375884";
        List<OrderItem> list =orderItemDao.queryOderItemByOrderId(orderId);
        list.forEach((item) -> {
            System.out.println(item);
        });
    }
}