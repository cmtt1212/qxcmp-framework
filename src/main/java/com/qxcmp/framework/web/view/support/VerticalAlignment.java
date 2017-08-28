package com.qxcmp.framework.web.view.support;

/**
 * 垂直对齐
 *
 * @author Aaric
 */
public enum VerticalAlignment {
    NONE(""),
    TOP("top aligned"),
    MIDDLE("middle aligned"),
    BOTTOM("bottom aligned");

    private String className;

    VerticalAlignment(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
