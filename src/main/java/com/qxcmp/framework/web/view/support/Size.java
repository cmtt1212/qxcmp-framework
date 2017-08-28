package com.qxcmp.framework.web.view.support;

/**
 * 大小
 *
 * @author Aaric
 */
public enum Size {
    NONE(""),
    TINY("tiny"),
    SMALL("small"),
    MEDIUM("medium"),
    LARGE("large"),
    HUGE("huge");

    private String className;

    Size(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
