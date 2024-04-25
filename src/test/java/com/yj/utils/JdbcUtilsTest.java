package com.yj.utils;

import com.yj.utils.JdbcUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author 阳健
 * 概要：
 *     JdbcUtils测试类
 */

public class JdbcUtilsTest {

    @Test
    public void testJdbcUtils() {
        for(int i = 0;i < 20 ;i++){
            //获取连接
            Connection conn = JdbcUtils.getConnection();
            //打印连接
            System.out.println(conn);
            //关闭连接
//            JdbcUtils.commitAndClose(conn);
        }
    }
}

