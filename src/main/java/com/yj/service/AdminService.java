package com.yj.service;

import com.yj.bean.User;

import java.util.List;

/**
 * 概要:
 *
 * @author 阳健
 */
public interface AdminService {
    /**
     * 管理员登录
     * @param user
     * @return
     */
    User adminLogin(User user);

    /**
     * 权限检查
     * @param user
     * @return
     */
    Boolean permissionCheck(User user);

    /**
     * 通过权限类型查询管理员列表
     * @param permissionType
     * @return
     */
    List<User> queryManagerByPermissionType(String permissionType);

    /**
     * 通过用户名查询管理员信息
     * @param userName
     * @return
     */
    User queryManagerByUserName(String userName);

    /**
     * 通过用户名更新管理员信息
     * @param user
     * @return
     */
    int updateManagerByUserName(User user);

    /**
     * 通过用户名删除一个管理员
     * @param userName
     * @return
     */
    int deleteManagerByUserName(String userName);

    /**
     * 添加管理员
     * @param user
     */
    void addManager(User user,String permissionType);
}
