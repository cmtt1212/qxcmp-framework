package com.qxcmp.framework.web.view.support;

/**
 * 设备类型
 *
 * @author Aaric
 */
public enum Device {
    NONE(""),
    COMPUTER("computer"),
    TABLET("tablet"),
    MOBILE("mobile");

    private String className;

    Device(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
