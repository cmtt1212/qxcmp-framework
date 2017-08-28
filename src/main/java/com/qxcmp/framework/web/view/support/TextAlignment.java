package com.qxcmp.framework.web.view.support;

/**
 * 文本对齐
 *
 * @author Aaric
 */
public enum TextAlignment {
    LEFT("left aligned"),
    RIGHT("right aligned"),
    CENTER("center aligned"),
    JUSTIFY("justified");

    private String className;

    TextAlignment(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
