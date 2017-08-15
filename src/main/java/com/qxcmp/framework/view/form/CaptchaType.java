package com.qxcmp.framework.view.form;

/**
 * 验证码输入框类型
 *
 * @author aaric
 * @see CaptchaFieldEntity
 */
public enum CaptchaType {
    IMAGE("image"),
    PHONE("phone");

    private String name;

    CaptchaType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
