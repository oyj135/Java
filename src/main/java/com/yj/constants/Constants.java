package com.yj.constants;

/**
 * @author 阳健
 * 概要：
 *     常量定义类
 */

public class Constants {
    /*
       订单状态
     */
    /**
     * 待支付
     */
    public static final Integer ORDER_STATUS_PENDING_PAYMENT = 0;

    /**
     * 待发货
     */
    public static final Integer ORDER_STATUS_PENDING_SHIP = 1;

    /**
     * 待收货
     */
    public static final Integer ORDER_STATUS_PENDING_ARRIVE = 2;

    /**
     * 已签收
     */
    public static final Integer ORDER_STATUS_SIGNED = 3;

    /**
     * 角色名称：管理员
     */
    public static final String CONST_ROLE_MANAGER = "管理员";

    /**
     * 角色名称：系统管理员
     */
    public static final String CONST_ROLE_ROOT = "系统管理员";

    /**
     * 角色名称：普通用户
     */
    public static final String CONST_ROLE_GENERAL_USER = "普通用户";

}
