package com.yj.dao.impl;

import com.yj.bean.Role;
import com.yj.dao.RoleDao;

/**
 * 概要:
 *    角色查询
 * @author 阳健
 */
public class RoleDaoImpl extends BaseDao implements RoleDao {

    @Override
    public Role queryRoleByUserName(String userName) {
        String sql = "select t1.roleid,t1.rolename from t_role t1 inner join t_user_role t2 on t1.roleid = t2.roleid where t2.username = ?";
        return queryForOne(Role.class,sql,userName);
    }

    @Override
    public Role queryRoleByRoleName(String roleName) {
        String sql = "select roleid,rolename from t_role where rolename = ?";
        return queryForOne(Role.class,sql,roleName);
    }
}
