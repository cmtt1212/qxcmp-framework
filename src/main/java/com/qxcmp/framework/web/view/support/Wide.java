package com.qxcmp.framework.web.view.support;

/**
 * 网格宽度
 *
 * @author Aaric
 */
public enum Wide {
    NONE(""),
    ONE("one wide"),
    TWO("two wide"),
    THREE("three wide"),
    FOUR("four wide"),
    FIVE("five wide"),
    SIX("six wide"),
    SEVEN("seven wide"),
    EIGHT("eight wide"),
    NINE("nine wide"),
    TEN("ten wide"),
    ELEVEN("eleven wide"),
    TWELVE("twelve wide"),
    THIRTEEN("thirteen wide"),
    FOURTEEN("fourteen wide"),
    FIFTEEN("fifteen wide"),
    SIXTEEN("sixteen wide");

    private String className;

    Wide(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
