package com.qxcmp.framework.view.component;

/**
 * 元素对齐方式
 *
 * @author aaric
 */
public enum ElementAlign {
    CENTER("center"),
    LEFT("left"),
    RIGHT("right"),
    JUSTIFY("justify");

    private String value;

    ElementAlign(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
