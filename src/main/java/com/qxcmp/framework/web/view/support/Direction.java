package com.qxcmp.framework.web.view.support;

/**
 * 方位
 *
 * @author Aaric
 */
public enum Direction {
    NONE(""),
    LEFT("left"),
    RIGHT("right"),
    TOP("top"),
    BOTTOM("bottom"),
    BELOW("below");

    private String className;

    Direction(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
