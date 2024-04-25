package com.yj.bean;

import java.time.LocalDateTime;

/**
 * 概要:
 *     权限管理模块：用户角色实体
 * @author 阳健
 */
public class UserRole {
    /** ID */
    private Integer id;
    /** 用户名称 */
    private String userName;
    /** 角色ID */
    private String roleId;
    /** 创建时间 */
    private LocalDateTime createDateTime;
    /** 更新时间 */
    private LocalDateTime updateDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
