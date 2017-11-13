package com.qxcmp.framework.user;

import lombok.Data;

import java.util.Date;

/**
 * 用户登录Token
 * <p>
 * 可用于网页授权登录
 *
 * @author Aaric
 */
@Data
public class UserLoginToken {

    private String userId;

    private Date dateExpired;

    public UserLoginToken(String userId, Date dateExpired) {
        this.userId = userId;
        this.dateExpired = dateExpired;
    }
}
