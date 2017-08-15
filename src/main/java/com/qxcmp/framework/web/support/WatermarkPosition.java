package com.qxcmp.framework.web.support;

/**
 * 水印位置，用于渲染使用
 *
 * @author aaric
 */
public class WatermarkPosition {

    private String name;

    private int ordinal;

    public WatermarkPosition(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
