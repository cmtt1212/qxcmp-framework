package com.qxcmp.framework.web.view.support;

/**
 * 项目数量
 *
 * @author Aaric
 */
public enum ItemCount {
    NONE(""),
    ONE("one item"),
    TWO("two item"),
    THREE("three item"),
    FOUR("four item"),
    FIVE("five item"),
    SIX("six item"),
    SEVEN("seven item"),
    EIGHT("eight item"),
    NINE("nine item"),
    TEN("ten item"),
    ELEVEN("eleven item"),
    TWELVE("twelve item"),
    THIRTEEN("thirteen item"),
    FOURTEEN("fourteen item"),
    FIFTEEN("fifteen item"),
    SIXTEEN("sixteen item");

    private String className;

    ItemCount(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
