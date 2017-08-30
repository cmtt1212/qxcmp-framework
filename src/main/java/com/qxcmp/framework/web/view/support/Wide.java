package com.qxcmp.framework.web.view.support;

/**
 * 网格宽度
 *
 * @author Aaric
 */
public enum Wide {
    NONE(""),
    ONE("one wide column"),
    TWO("two wide column"),
    THREE("three wide column"),
    FOUR("four wide column"),
    FIVE("five wide column"),
    SIX("six wide column"),
    SEVEN("seven wide column"),
    EIGHT("eight wide column"),
    NINE("nine wide column"),
    TEN("ten wide column"),
    ELEVEN("eleven wide column"),
    TWELVE("twelve wide column"),
    THIRTEEN("thirteen wide column"),
    FOURTEEN("fourteen wide column"),
    FIFTEEN("fifteen wide column"),
    SIXTEEN("sixteen wide column");

    private String className;

    Wide(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
