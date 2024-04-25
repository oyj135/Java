package com.yj.dao;

import com.yj.bean.User;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *    UserDao接口类
 */

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     * @param userName  用户名
     * @return          如果为null则用户不存在，否则结果为查询的用户信息。
     */
    User queryUserByUserName(String userName);

    /**
     * 根据用户名和密码查询用户信息
     * @param userName  用户名
     * @param password  密码
     * @return          如果为null则用户名不存在或者密码错误，否则该用户存在。
     */
    User queryUserByUserNameAndPassword(String userName,String password);

    /**
     * 保存用户信息
     * @param user  用户信息
     * @return      如果为-1,保存失败。否则保存成功。
     */
    int saveUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 删除一个用户
     * @param userName
     * @return
     */
    int deleteUser(String userName);

    /**
     * 根据权限类型查询用户信息
     * @param permissionType  权限类型
     * @return                如果为null则用户不存在，否则结果为查询的用户信息。
     */
    List<User> queryUserByPermissionType(String permissionType);

}
