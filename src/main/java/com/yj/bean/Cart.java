package com.yj.bean;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 阳健
 * 概要：
 *     购物车
 */

public class Cart {

    /**
     * 购物车商品信息
     */
    private Map<Integer,CartItem> items = new LinkedHashMap<Integer,CartItem>();

    /**
     * 默认构造器
     */
    public Cart() {
    }

    /**
     * 重写toString方法
     * @return
     */
    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }

    /**
     * 添加到购物车
     * @param cartItem  商品信息
     */
    public void addItem(CartItem cartItem){

        CartItem item = items.get(cartItem.getGoodsId());

        //相同的商品存在的情况
        if(item != null){
            //数量的累加
            item.setGoodsCount(item.getGoodsCount() + 1);

            //金额的累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getGoodsCount())));

        }else{
            //直接添加
            items.put(cartItem.getGoodsId(),cartItem);
        }

    }

    /**
     * 删除商品
     * @param goodsId  商品ID
     */
    public void deleteItem(Integer goodsId){
        //购物车商品中含有该商品
        if(items.containsKey(goodsId)){
            //删除该商品
            items.remove(goodsId);

        }
    }

    /**
     * 修改购物车商品数量
     * @param goodsId     商品ID
     * @param goodsCount  商品件数
     */
    public void updateItem(Integer goodsId,Integer goodsCount){
        CartItem item = items.get(goodsId);

        //相同的商品存在的情况
        if(item != null){
            //数量的累加
            item.setGoodsCount(goodsCount);

            //金额的累加
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(goodsCount)));

        }

    }

    /**
     * 清空购物车
     */
    public void clear(){
        items.clear();

    }

    /**
     * 获取购物车商品总件数
     * @return  商品件数
     */
    public Integer getTotalCount() {
        Integer totalCount = 0;

        //遍历购物车的所有商品进行累加计算
        for(Map.Entry<Integer,CartItem> entry : items.entrySet()){
            totalCount += entry.getValue().getGoodsCount();
        }

        return totalCount;
    }

    /**
     * 获取购物车商品金额
     * @return
     */
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);

        //遍历购物车所有商品进行金额的累加
        for(CartItem cartItem : items.values()){
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        return totalPrice;
    }

    /**
     * 获取购物车商品信息
     * @return
     */
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    /**
     * 设置购物车商品信息
     * @param items
     */
    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }
}
