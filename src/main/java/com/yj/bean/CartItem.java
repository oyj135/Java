package com.yj.bean;

import java.math.BigDecimal;

/**
 * @author 阳健
 * 概要：
 *     购物车商品信息
 */

public class CartItem {

    /**
     * 商品Id
     */
    private Integer goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer goodsCount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 金额
     */
    private BigDecimal totalPrice;

    /**
     * 默认构造器
     */
    public CartItem() {

    }

    /**
     * 全参数构造器
     * @param goodsId     商品ID
     * @param goodsName   商品名称
     * @param goodsCount  商品件数
     * @param price       单价
     * @param totalPrice  总金额
     */
    public CartItem(Integer goodsId, String goodsName, Integer goodsCount, BigDecimal price, BigDecimal totalPrice) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsCount = goodsCount;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    /**
     * 重写toString
     * @return 对象属性值
     */
    @Override
    public String toString() {
        return "CartItem{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
