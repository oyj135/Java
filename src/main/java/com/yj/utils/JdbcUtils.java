package com.yj.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 阳健
 * 概要：
 *     jdbc工具类
 */

public class JdbcUtils {

    /**
     * 数据库连接池
     */
    private static DruidDataSource dataSource;

    /**
     * 事务管理线程
     */
    private static final ThreadLocal<Connection> CONNS = new ThreadLocal<Connection>();

    /**
     * 创建数据库连接池静态代码块
     */
    static {
        try {
            //创建properti对象
            Properties properties = new Properties();
            //获取jsbc配置文件数据流
            InputStream inputSteam = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //导入jdbc配置文件
            properties.load(inputSteam);
            //创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接池中连接
     */
    /**
     * 获取数据库连接池中连接
     * @return 如果不为null，则获取数据库连接成功.<br/>
     *         如果为null,则获取数据库连接失败.<br/>
     */
    public static Connection getConnection(){

        //0.从线程中取得连接
        Connection conn = CONNS.get();

        if(conn == null){

            try {

                //1.通过连接池获取连接
                conn = dataSource.getConnection();
                //2.把连接存放到线程中
                CONNS.set(conn);
                //3.设定手动管理事务
                conn.setAutoCommit(false);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return conn;
    }

    /**
     * 提交事务并关闭连接
     */
    public static void commitAndClose(){

        //0.从线程中取得连接
        Connection conn = CONNS.get();

        if(conn != null){

            try {
                //1.提交事务
                conn.commit();

            } catch (SQLException e) {
                e.printStackTrace();
            }finally{

                try {
                    //2.关闭连接
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        //一定要执行remove方法，否则报错。（Tomcat底层使用了线程池技术。）
        CONNS.remove();
    }

    /**
     * 回滚事务并关闭连接
     */
    public static void rollbackAndClose(){

        //0.从线程中取得连接
        Connection conn = CONNS.get();

        if(conn != null){

            try {
                //1.回滚事务
                conn.rollback();

            } catch (SQLException e) {
                e.printStackTrace();
            }finally{

                try {
                    //2.关闭连接
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        //一定要执行remove方法，否则报错。（Tomcat底层使用了线程池技术。）
        CONNS.remove();
    }
}
