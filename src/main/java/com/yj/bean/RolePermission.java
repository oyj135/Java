package com.yj.bean;

import java.time.LocalDateTime;

/**
 * 概要:
 *    权限管理模块：角色权限实体
 * @author 阳健
 */
public class RolePermission {
    /** ID */
    private Integer id;
    /** 角色ID */
    private String roleId;
    /** 权限ID */
    private String permissionId;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
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
