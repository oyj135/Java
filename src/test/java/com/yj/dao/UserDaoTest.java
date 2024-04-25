package com.yj.dao;

import com.yj.bean.User;
import com.yj.dao.UserDao;
import com.yj.dao.impl.UserDaoImpl;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 阳健
 * 概要：
 *     UserDao测试类
 */

public class UserDaoTest {


    @Test
    public void queryUserByUserName() {
        UserDao userDao = new UserDaoImpl();

        if(userDao.queryUserByUserName("admin") != null){
            System.out.println("admin用户存在！");
        }else{
            System.out.println("admin用户不存在！");
        }

        if(userDao.queryUserByUserName("gust") != null){
            System.out.println("gust用户存在！");
        }else{
            System.out.println("gust用户不存在！");
        }

    }

    @Test
    public void queryUserByUserNameAndPassword() {
        UserDao userDao = new UserDaoImpl();

        if(userDao.queryUserByUserNameAndPassword("admin","admin") != null){
            System.out.println("登录成功");
        }else{
            System.out.println("用户名或者密码错误！");
        }

        if(userDao.queryUserByUserNameAndPassword("admin","123456") != null){
            System.out.println("登录成功");
        }else{
            System.out.println("用户名或者密码错误！");
        }
    }

    @Test
    public void saveUser() {

        User user = new User();
        user.setUsername("gust");
        user.setPassword("gust");
        user.setEmail("gust@test.com");

        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        user.setRegistTime(dateFormat.format(date.getTime()));

        UserDao userDao = new UserDaoImpl();

        if(userDao.queryUserByUserName(user.getUsername()) != null){
            System.out.println("用户已经被注册，请更换用户名！");
        }else {
            if (-1 != userDao.saveUser(user)) {
                System.out.println("用户注册成功！");
            } else {
                System.out.println("用户注册失败！");
            }
        }


    }
}