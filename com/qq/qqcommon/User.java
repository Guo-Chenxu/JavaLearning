package com.qq.qqcommon;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 */
public class User implements Serializable {
    /**
     * 序列化兼容性
     */
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户密码
     */
    private String password;

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
