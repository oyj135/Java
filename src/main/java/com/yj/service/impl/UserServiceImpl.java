package com.yj.service.impl;


import com.yj.bean.Role;
import com.yj.bean.User;
import com.yj.bean.UserRole;
import com.yj.constants.Constants;
import com.yj.dao.RoleDao;
import com.yj.dao.UserDao;
import com.yj.dao.UserRoleDao;
import com.yj.dao.impl.RoleDaoImpl;
import com.yj.dao.impl.UserDaoImpl;
import com.yj.dao.impl.UserRoleDaoImpl;
import com.yj.service.UserService;

/**
 * @author 阳健
 * 概要：
 *     业务处理service
 */

public class UserServiceImpl implements UserService {

    /**
     * UserDao
     */
    UserDao userDao = new UserDaoImpl();

    /**
     *
     * roleDao
     */
    RoleDao roleDao = new RoleDaoImpl();

    /**
     * userRoleDao
     */
    UserRoleDao userRoleDao = new UserRoleDaoImpl();

    @Override
    public void registUser(User user) {

        UserRole userRole=new UserRole();
        userRole.setUserName(user.getUsername());

        //保存用户信息
        userDao.saveUser(user);
        //查询普通用户的角色ID
        Role role = roleDao.queryRoleByRoleName(Constants.CONST_ROLE_GENERAL_USER);
        userRole.setRoleId(role.getRoleId());
        //保存用户角色信息
        userRoleDao.add(userRole);

    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUserNameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUserName(String userName) {

        if(null != userDao.queryUserByUserName(userName)){
            return true;
        }
        return false;
    }
}
