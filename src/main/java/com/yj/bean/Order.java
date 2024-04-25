package com.yj.bean;

import java.math.BigDecimal;

/**
 * @author 阳健
 * 概要：
 *      订单类
 */

public class Order {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 订单创建时间
     */
    private String createTime;

    /**
     * 订单总金额
     */
    private BigDecimal price;

    /**
     * 订单状态
     *   0：未发货
     *   1：已发货
     *   2：已签收
     */
    private Integer status;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 创建默认构造器
     */
    public Order() {
    }

    /**
     * 创建全参数订单构造器
     * @param orderId     订单编号
     * @param createTime  创建时间
     * @param price       单价
     * @param status      订单状态
     * @param userId      用户ID
     */
    public Order(String orderId, String createTime, BigDecimal price, Integer status, Integer userId) {
        this.orderId = orderId;
        this.createTime = createTime;
        this.price = price;
        this.status = status;
        this.userId = userId;
    }

    /**
     * 重写toString
     * @return 订单对象字符串
     */
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", createTime=" + createTime +
                ", price=" + price +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
