package com.yj.dao;

import com.yj.bean.UserRole;

/**
 * 概要:
 *   用户角色表Dao
 * @author 阳健
 */
public interface UserRoleDao {

    /**
     * 删除用户角色
     * @param userName
     * @return
     */
    int delete(String userName);

    /**
     * 添加用户角色
     * @param userRole
     * @return
     */
    int add(UserRole userRole);
}
