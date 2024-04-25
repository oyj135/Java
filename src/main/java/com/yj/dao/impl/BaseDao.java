package com.yj.dao.impl;

import com.yj.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 阳健
 * 概要：
 *     BaseDao类
 *
 */

public abstract class BaseDao {

    /**
     * 使用apache的DbUtils操作数据库
     */
    private final QueryRunner queryRunner = new QueryRunner();

    /**
     * 用来执行insert，update，delete语句
     * @param sql 要执行的sql文
     * @param args sql的参数
     * @return 如果查询到结果返回>1的值.<br/>
     *         返回-1 表示没有查询到结果。
     */
    public int update(String sql,Object ... args){

        //获取连接
        Connection conn = JdbcUtils.getConnection();

        //执行sql
        try {
            return queryRunner.update(conn,sql,args);
        } catch (SQLException e) {
            e.printStackTrace();
            //抛出异常供外部捕获
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询返回一个JavaBean的sql
     * @param type JavaBean类型
     * @param sql  执行的sql文
     * @param args sql的参数
     * @param <T>  类型的泛型
     * @return     返回一个T类型的对象
     */
    public <T> T queryForOne(Class<T> type,String sql,Object ... args){
        //获取连接
        Connection conn = JdbcUtils.getConnection();

        //执行sql
        try {
            return queryRunner.query(conn,sql,new BeanHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            //抛出异常供外部捕获
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回一个多个JavaBean的List结果集合
     * @param type JavaBean类型
     * @param sql  执行的sql文
     * @param args sql的参数
     * @param <T>  类型的泛型
     * @return     返回一个List
     */
    public <T> List<T> queryForList(Class<T> type,String sql,Object ... args){
        //获取连接
        Connection conn = JdbcUtils.getConnection();

        //执行sql
        try {
            return queryRunner.query(conn,sql,new BeanListHandler<T>(type),args);
        } catch (SQLException e) {
            e.printStackTrace();
            //抛出异常供外部捕获
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回一个一行一列的结果
     * @param sql 查询的sql
     * @param args  sql参数
     * @return 返回一个一行一列的结果
     */
    public Object queryForSingleValue(String sql,Object ... args){
        //获取连接
        Connection conn = JdbcUtils.getConnection();

        //执行sql
        try {
            return queryRunner.query(conn,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            e.printStackTrace();
            //抛出异常供外部捕获
            throw new RuntimeException(e);
        }
    }

}
