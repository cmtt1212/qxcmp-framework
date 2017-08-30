package com.qxcmp.framework.web.view.support;

/**
 * 对齐方式
 *
 * @author Aaric
 */
public enum Alignment {
    NONE(""),
    LEFT("left aligned"),
    RIGHT("right aligned"),
    CENTER("center aligned"),
    JUSTIFY("justified");

    private String className;

    Alignment(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
