package com.qxcmp.framework.web.view.support;

/**
 * 元素浮动
 *
 * @author Aaric
 */
public enum Floating {
    NONE(""),
    LEFT("left floated"),
    RIGHT("right floated");

    private String className;

    Floating(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
