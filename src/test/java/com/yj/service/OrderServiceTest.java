package com.yj.service;

import com.yj.bean.Cart;
import com.yj.bean.CartItem;
import com.yj.service.OrderService;
import com.yj.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author 阳健
 * 概要：
 *    创建订单service测试
 */

public class OrderServiceTest {

    OrderService orderService= new OrderServiceImpl();

    @Test
    public void createOrder() {
        Cart cart = new Cart();

        CartItem cartItem = new CartItem();
        cartItem.setGoodsId(1);
        cartItem.setGoodsName("vue.js从入门到精通");
        cartItem.setPrice(new BigDecimal(99.99));
        cartItem.setTotalPrice(new BigDecimal(99.99));
        cartItem.setGoodsCount(1);
        cart.addItem(cartItem);

        Integer userId = 1;
        orderService.createOrder(cart,userId);

    }
}