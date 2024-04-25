package com.yj.dao.impl;

import com.yj.bean.UserRole;
import com.yj.dao.UserRoleDao;

/**
 * 概要:
 *
 * @author 阳健
 */
public class UserRoleDaoImpl extends BaseDao implements UserRoleDao {

    @Override
    public int delete(String userName) {
        String sql = "delete from t_user_role where username = ?";
        return update(sql,userName);
    }

    @Override
    public int add(UserRole userRole) {
        String sql = "insert into t_user_role(username,roleid,createdatetime) values(?,?,current_timestamp)";
        return update(sql,userRole.getUserName(),userRole.getRoleId());
    }
}
