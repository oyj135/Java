package com.yj.service;

import com.yj.bean.User;

/**
 * @author 阳健
 * 概要：
 *    用户接口
 */

public interface UserService {

    /**
     * 注册用户功能
     * @param user
     */
    void registUser(User user);

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 检查用户是否已经存在
     * @param userName
     * @return true:表示用户已经存在<br/>
     *         false:表示用户不存在
     *
     */
    boolean existsUserName(String userName);

}
