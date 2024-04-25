package com.yj.bean;

import java.time.LocalDateTime;

/**
 * 概要:
 *     权限管理模块：权限实体
 * @author 阳健
 */
public class Permission {

    /** 权限ID */
    private String permissionId;
    /** 权限名称 */
    private String permissionName;
    /** 权限备注 */
    private String permissionRemarker;
    /** 创建时间 */
    private LocalDateTime createDateTime;
    /** 更新时间 */
    private LocalDateTime updateDateTime;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionRemarker() {
        return permissionRemarker;
    }

    public void setPermissionRemarker(String permissionRemarker) {
        this.permissionRemarker = permissionRemarker;
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
