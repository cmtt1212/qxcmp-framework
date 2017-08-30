package com.qxcmp.framework.web.view.support;

/**
 * 设备可见性
 *
 * @author Aaric
 */
public enum DeviceVisibility {
    NONE(""),
    LARGE_ONLY("computer"),
    COMPUTER_ONLY("computer"),
    TABLET_ONLY("tablet"),
    MOBILE_ONLY("mobile");

    private String className;

    DeviceVisibility(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
