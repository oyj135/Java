package com.yj.dao.impl;

import com.yj.bean.User;
import com.yj.dao.UserDao;

import java.util.List;

/**
 * @author 阳健
 * 概要：
 *    UserDao接口的实现类
 */

public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public User queryUserByUserName(String userName) {

        String sql = "select `id`,`username`,`password`,`email`,`registtime`,`updatetime` from t_user where username = ?";

        return queryForOne(User.class,sql,userName);

    }

    @Override
    public User queryUserByUserNameAndPassword(String userName, String password) {
        String sql = "select `id`,`username`,`password`,`email` from t_user where username = ? and password = ?";

        return queryForOne(User.class,sql,userName,password);
    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(`username`,`password`,`email`,`registtime`) values(?,?,?,current_timestamp)";

        return update(sql,user.getUsername(),user.getPassword(),user.getEmail());
    }

    @Override
    public int updateUser(User user) {
        String sql = "update t_user set password = ?,email = ?,updatetime = current_timestamp where username = ?";
        return update(sql,user.getPassword(),user.getEmail(),user.getUsername());
    }

    @Override
    public int deleteUser(String userName) {
        String sql = "delete from t_user where username = ?";
        return update(sql,userName);
    }

    @Override
    public List<User> queryUserByPermissionType(String permissionType) {
        String sql = "select T1.username,T1.password,T1.email,T1.registtime,T1.updateTime from t_user T1 inner join BookShop.t_user_role T2 on T1.username = T2.username inner join BookShop.t_role T3 on T2.roleid = T3.roleid where T3.rolename = ?";
        return queryForList(User.class,sql,permissionType);
    }


}
