package com.yj.dao;

import com.yj.bean.Order;
import com.yj.dao.OrderDao;
import com.yj.dao.impl.OrderDaoImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     OrderDao测试类
 */

public class OrderDaoTest {

    OrderDao orderDao = new OrderDaoImpl();

    @Test
    public void saveOreder() {
        Date currentDate = new Date();
        SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //格式化当前日期
        SimpleDateFormat.format(currentDate);

        //创建订单
        Order order = new Order("1000-20201124160000-00002",SimpleDateFormat.format(currentDate),new BigDecimal(100),0,1);

        orderDao.saveOreder(order);

    }


    @Test
    public void queryOrdersByUserId() {

        Integer orderId = 1;
        List<Order> list = orderDao.queryOrdersByUserId(orderId);

        list.forEach((item) -> {
            System.out.println(item);
        });

    }
}