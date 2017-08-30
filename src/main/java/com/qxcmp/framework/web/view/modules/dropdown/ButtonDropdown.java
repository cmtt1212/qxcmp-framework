package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 按钮下拉框
 * <p>
 * 用于在一般情况下使用
 *
 * @author Aaric
 */
@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
}
