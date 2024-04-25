package com.yj.bean;

import java.time.LocalDateTime;

/**
 * 概要:
 *     权限管理模块：角色实体
 * @author 阳健
 */
public class Role {

    /** 角色ID */
    private String roleId;
    /** 角色名称 */
    private String roleName;
    /** 角色备注 */
    private String roleRemarker;
    /** 创建时间 */
    private LocalDateTime createDateTime;
    /** 更新时间 */
    private LocalDateTime updateDateTime;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemarker() {
        return roleRemarker;
    }

    public void setRoleRemarker(String roleRemarker) {
        this.roleRemarker = roleRemarker;
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
