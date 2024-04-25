package com.yj.bean;

import com.yj.bean.Cart;
import com.yj.bean.CartItem;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author 阳健
 */

public class CartTest {

    @Test
    public void addItem() {
        //创建购物车对象
        Cart cart = new Cart();

        //创建商品项
        CartItem cartitem1 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem2 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem3 = new CartItem(2,"Python从入门到精通",1,new BigDecimal(30),new BigDecimal(30));

        //添加商品到购物车
        cart.addItem(cartitem1);
        cart.addItem(cartitem2);
        cart.addItem(cartitem3);

        //输出购物车信息
        System.out.println(cart.toString());
    }

    @Test
    public void deleteItem() {
        //创建购物车对象
        Cart cart = new Cart();

        //创建商品项
        CartItem cartitem1 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem2 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem3 = new CartItem(2,"Python从入门到精通",1,new BigDecimal(30),new BigDecimal(30));

        //添加商品到购物车
        cart.addItem(cartitem1);
        cart.addItem(cartitem2);
        cart.addItem(cartitem3);

        cart.deleteItem(1);

        //输出购物车信息
        System.out.println(cart.toString());
    }

    @Test
    public void updateItem() {
        //创建购物车对象
        Cart cart = new Cart();

        //创建商品项
        CartItem cartitem1 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem2 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem3 = new CartItem(2,"Python从入门到精通",1,new BigDecimal(30),new BigDecimal(30));

        //添加商品到购物车
        cart.addItem(cartitem1);
        cart.addItem(cartitem2);
        cart.addItem(cartitem3);

        cart.updateItem(2,3);

        //输出购物车信息
        System.out.println(cart.toString());
    }

    @Test
    public void clear() {
        //创建购物车对象
        Cart cart = new Cart();

        //创建商品项
        CartItem cartitem1 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem2 = new CartItem(1,"java从入门到精通",1,new BigDecimal(20),new BigDecimal(20));
        CartItem cartitem3 = new CartItem(2,"Python从入门到精通",1,new BigDecimal(30),new BigDecimal(30));

        //添加商品到购物车
        cart.addItem(cartitem1);
        cart.addItem(cartitem2);
        cart.addItem(cartitem3);

        cart.clear();

        //输出购物车信息
        System.out.println(cart.toString());
    }


}