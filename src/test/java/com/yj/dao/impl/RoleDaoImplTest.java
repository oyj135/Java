package com.yj.dao.impl;

import com.yj.bean.Role;
import com.yj.dao.RoleDao;
import com.yj.dao.impl.RoleDaoImpl;
import org.junit.Test;

/**
 * @author 阳健
 */
public class RoleDaoImplTest {

    @Test
    public void queryRoleByUserName() {
        RoleDao roleDao = new RoleDaoImpl();
        Role role= roleDao.queryRoleByUserName("admin");
        System.out.println(role.getRoleName());
    }
}