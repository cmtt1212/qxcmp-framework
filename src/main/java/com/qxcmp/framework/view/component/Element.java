package com.qxcmp.framework.view.component;

import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.Objects;

/**
 * 基本HTML元素
 * <p>
 * 一个元素占用一行
 * <p>
 * 支持设置文本对齐和颜色
 *
 * @author aaric
 */
@Data
@Builder
public class Element {

    private ElementType type;

    private ElementAlign align;

    private Color color;

    private String content;

    public String getColorHex() {
        return Objects.isNull(color) ? "#000000" : String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
