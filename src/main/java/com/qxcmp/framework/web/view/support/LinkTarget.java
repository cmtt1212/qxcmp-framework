package com.qxcmp.framework.web.view.support;

/**
 * 超链接打开方式
 *
 * @author Aaric
 */
public enum LinkTarget {
    NONE(""),
    BLANK("_blank"),
    SELF("_self"),
    PARENT("_parent"),
    TOP("_top");

    private String value;

    LinkTarget(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
