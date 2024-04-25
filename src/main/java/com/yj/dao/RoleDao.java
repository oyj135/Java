package com.yj.dao;

import com.yj.bean.Role;

/**
 * 概要:
 *
 * @author 阳健
 */
public interface RoleDao {

    /**
     * 通过用户名查询角色
     * @param userName
     * @return
     */
    Role queryRoleByUserName(String userName);


    Role queryRoleByRoleName(String roleName);

}
