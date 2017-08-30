package com.qxcmp.framework.web.view.support;

/**
 * 颜色
 *
 * @author Aaric
 */
public enum Color {
    NONE(""),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yelow"),
    OLIVE("olive"),
    GREEN("green"),
    TEAL("teal"),
    BLUE("blue"),
    VIOLET("violet"),
    PURPLE("purple"),
    PINK("pink"),
    BROWN("brown"),
    GREY("grey"),
    BLACK("black");

    private String className;

    Color(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
