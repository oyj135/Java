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
import com.yj.service.AdminService;

import java.util.List;

/**
 * 概要:
 *
 * @author 阳健
 */
public class AdminServiceImpl implements AdminService {

    /**
     * 用户Dao
     */
    UserDao userDao = new UserDaoImpl();

    /**
     * 角色Dao
     */
    RoleDao roleDao = new RoleDaoImpl();

    /**
     * 用户角色Dao
     */
    UserRoleDao userRoleDao = new UserRoleDaoImpl();

    @Override
    public User adminLogin(User user) {
        return userDao.queryUserByUserNameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    /**
     * 系统管理员，管理员权限检查
     */
    public Boolean permissionCheck(User user) {
       String userName = null;
        userName = user.getUsername();

        //根据用户名查询角色
        Role role = roleDao.queryRoleByUserName(userName);
        if(role != null ){
            //如果角色为管理员或者根角色，返回true
            if(Constants.CONST_ROLE_ROOT.equals(role.getRoleName()) ||
                    Constants.CONST_ROLE_MANAGER.equals(role.getRoleName())){
                return true;
            }else{
                return false;
            }
        }else{
            return null;
        }
    }

    @Override
    public List<User> queryManagerByPermissionType(String permissionType) {
        return userDao.queryUserByPermissionType(permissionType);
    }

    @Override
    public User queryManagerByUserName(String userName) {
        return userDao.queryUserByUserName(userName);
    }

    @Override
    public int updateManagerByUserName(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteManagerByUserName(String userName) {
        //删除用户角色表的数据
        userRoleDao.delete(userName);
        //删除用户表的数据
        return userDao.deleteUser(userName);
    }

    @Override
    public void addManager(User user,String permissionType) {

        //查询对应权限类型的角色ID
        Role role = roleDao.queryRoleByRoleName(permissionType);
        //设置用户角色
        UserRole userRole = new UserRole();
        userRole.setUserName(user.getUsername());
        userRole.setRoleId(role.getRoleId());
        //保存用户
        userDao.saveUser(user);
        //保存用户角色
        userRoleDao.add(userRole);

    }


}
