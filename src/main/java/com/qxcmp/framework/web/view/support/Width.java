package com.qxcmp.framework.web.view.support;

/**
 * 宽度
 *
 * @author Aaric
 */
public enum Width {
    NONE(""),
    VERY_THIN(" thin"),
    THIN(" very thin"),
    WIDE(" wide"),
    VERY_WIDE(" very wide");

    private String value;

    Width(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
