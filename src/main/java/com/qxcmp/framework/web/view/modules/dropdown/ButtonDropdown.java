package com.qxcmp.framework.web.view.modules.dropdown;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.modules.dropdown.item.AbstractItem;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 按钮下拉框
 * <p>
 * 用于在一般情况下使用
 *
 * @author Aaric
 */
@Getter
@Setter
public class ButtonDropdown extends AbstractDropdown {

    /**
     * 按钮文本
     */
    private String text;

    /**
     * 按钮图标 - 可选 - 默认显示在左侧
     */
    private String icon;

    /**
     * 将图标显示在按钮右侧
     */
    private boolean rightIcon;

    /**
     * 是否开启搜索
     */
    private boolean search;

    /**
     * 是否支持多选，可以与搜索结合使用
     */
    private boolean multiple;

    /**
     * 颜色
     */
    private Color color = Color.NONE;

    /**
     * 是否显示指针
     */
    private boolean pointing;

    /**
     * 指针方向
     */
    private Direction pointingDirection = Direction.NONE;

    /**
     * 下拉选项
     */
    private List<AbstractItem> items = Lists.newArrayList();

    @Override
    public String getFragmentName() {
        return "button";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (rightIcon) {
            stringBuilder.append(" right labeled");
        } else {
            stringBuilder.append(" labeled");
        }

        if (StringUtils.isNotBlank(icon)) {
            stringBuilder.append(" icon");
        }

        if (search) {
            stringBuilder.append(" search");
        }

        if (multiple) {
            stringBuilder.append(" multiple");
        }


        stringBuilder.append(color.toString());

        if (pointing) {
            if (StringUtils.isNotBlank(pointingDirection.getClassName())) {
                stringBuilder.append(" ").append(pointingDirection.getClassName());
            }
            stringBuilder.append(" pointing");
        }

        stringBuilder.append(" dropdown button");

        return stringBuilder.toString();
    }
}
