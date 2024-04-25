package com.yj.bean;

/**
 * @author 阳健
 * 概要：
 *     用户信息类
 */

public class User {
    /**
     * ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮件地址
     */
    private String email;

    /**
     * 创建时间
     */
    private String registTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 无参构造器
     */
    public User() {

    }

    /**
     * 有参数构造器
     * @param id          ID
     * @param username    用户名
     * @param password    密码
     * @param email       邮件地址
     * @param registTime  注册事件
     * @param updateTime  更新时间
     */
    public User(Integer id, String username, String password, String email, String registTime, String updateTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registTime = registTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("username:" + username + " ").append("password:" + password + " ").append("email:" + email + " ").append("registTime:" + registTime + " ").toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
