package com.yj.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author 阳健
 * 概要：
 *    Web层的工具类
 */

public class WebUtils {
    /**
     * 把参数Map封装到JavaBean
     * @param parameterMap  要封装的Map
     * @param bean          封装到的JavaBean对象
     * @return              封装后的JavaBean对象
     */
    public static Object copygetParameterMapToBean(Map<String,? extends Object> parameterMap, Object bean){

        //使用beanUtils进行JavaBean的封装
        try {
            BeanUtils.populate(bean,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 把Sting类型转换为int类型
     * @param var1 要转换的字符串
     * @param var2 默认值
     * @return     如果要转换的字符串不为null或者长度不为0 的话，则返回转换后得值。<br/>
     *             如果为null或者长度为0则返回默认值。
     */
    public static int parseInt(String var1, int var2){
        if(var1 != null && var1.length() != 0) {
            return Integer.parseInt(var1);
        }
        return var2;
    }


}
