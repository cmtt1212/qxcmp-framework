package com.qxcmp.framework.web.view.containers;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
public class Grid extends AbstractGrid {

    /**
     * 是否在列之间显示分隔符
     */
    private boolean divided;

    /**
     * 是否在行之间显示分隔符，需要使用行
     */
    private boolean verticallyDivided;

    /**
     * 是否显示网格边框，需要使用行
     */
    private boolean celled;

    /**
     * 是否只显示网格内部边框
     */
    private boolean internallyCelled;

    /**
     * 是否为容器样式
     */
    private boolean container;

    /**
     * 是否增加网格内边距
     * <p>
     * 该属性会同时增加水平和垂直内边距
     */
    private boolean padded;

    /**
     * 是否增加网格垂直内边距
     */
    private boolean verticallyPadded;

    /**
     * 是否增加网格水平内边距
     */
    private boolean horizontallyPadded;

    /**
     * 网格列数
     */
    private ColumnCount columnCount = ColumnCount.NONE;

    /**
     * 是否开启等宽列
     */
    private boolean equalWidth;

    /**
     * 是否增加槽宽度
     */
    private boolean relaxed;

    /**
     * 是否大幅增加槽宽度
     */
    private boolean veryRelaxed;

    /**
     * 是否居中列元素
     * <p>
     * 当列没有占满行时，将居中显示列元素
     */
    private boolean centered;

    /**
     * 是否在非电脑端双倍排列列元素
     */
    private boolean doubling;

    /**
     * 是否自动堆叠
     * <p>
     * 将在移动端尺寸下自动堆叠
     */
    private boolean stackable;

    /**
     * 是否在电脑端颠倒列顺序
     */
    private boolean computerReversed;

    /**
     * 是否在平板端颠倒列顺序
     */
    private boolean tabletReversed;

    /**
     * 是否在移动端颠倒列顺序
     */
    private boolean mobileReversed;

    /**
     * 是否在电脑端颠倒行顺序
     */
    private boolean computerVerticallyReversed;

    /**
     * 是否在平板端颠倒行顺序
     */
    private boolean tabletVerticallyReversed;

    /**
     * 是否在移动端颠倒行顺序
     */
    private boolean mobileVerticallyReversed;


    /**
     * 网格内容
     * <p>
     * 网格内容必须为行或者列
     */
    private List<AbstractGridElement> components = Lists.newArrayList();

    public Grid() {
        super("grid");
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (divided) {
            stringBuilder.append(" divided");
        }

        if (verticallyDivided) {
            stringBuilder.append(" vertically divided");
        }

        if (celled) {
            stringBuilder.append(" celled");
        }

        if (internallyCelled) {
            stringBuilder.append(" internally celled");
        }

        if (container) {
            stringBuilder.append(" container");
        }

        if (StringUtils.isNotBlank(columnCount.getClassName())) {
            stringBuilder.append(" ").append(columnCount.getClassName());
        }

        if (equalWidth) {
            stringBuilder.append(" equal width");
        }

        if (padded) {
            stringBuilder.append(" padded");
        }

        if (verticallyPadded) {
            stringBuilder.append(" vertically padded");
        }

        if (horizontallyPadded) {
            stringBuilder.append(" horizontally padded");
        }

        if (relaxed) {
            stringBuilder.append(" relaxed");
        }

        if (veryRelaxed) {
            stringBuilder.append(" very relaxed");
        }

        if (StringUtils.isNotBlank(alignment.toString())) {
            stringBuilder.append(" ").append(alignment.toString());
        }

        if (StringUtils.isNotBlank(verticalAlignment.getClassName())) {
            stringBuilder.append(" ").append(verticalAlignment.getClassName());
        }

        if (doubling) {
            stringBuilder.append(" doubling");
        }

        if (stackable) {
            stringBuilder.append(" stackable");
        }

        if (computerReversed) {
            stringBuilder.append(" computer reversed");
        }
        if (tabletReversed) {
            stringBuilder.append(" tablet reversed");
        }
        if (mobileReversed) {
            stringBuilder.append(" mobile reversed");
        }
        if (computerVerticallyReversed) {
            stringBuilder.append(" computer vertically reversed");
        }
        if (tabletVerticallyReversed) {
            stringBuilder.append(" tablet vertically reversed");
        }
        if (mobileVerticallyReversed) {
            stringBuilder.append(" mobile vertically reversed");
        }

        stringBuilder.append(" grid");

        return stringBuilder.toString();
    }
}
