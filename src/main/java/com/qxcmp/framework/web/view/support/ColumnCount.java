package com.qxcmp.framework.web.view.support;

/**
 * 网格列数量
 *
 * @author Aaric
 */
public enum ColumnCount {
    NONE(""),
    ONE("one column"),
    TWO("two column"),
    THREE("three column"),
    FOUR("four column"),
    FIVE("five column"),
    SIX("six column"),
    SEVEN("seven column"),
    EIGHT("eight column"),
    NINE("nine column"),
    TEN("ten column"),
    ELEVEN("eleven column"),
    TWELVE("twelve column"),
    THIRTEEN("thirteen column"),
    FOURTEEN("fourteen column"),
    FIFTEEN("fifteen column"),
    SIXTEEN("sixteen column");

    private String className;

    ColumnCount(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
