package com.qxcmp.framework.web.view.elements.menu;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
public class Menu extends AbstractMenu {

    /**
     * 是否为纯文本菜单
     * <p>
     * 该属性会移除菜单的边框
     */
    private boolean text;

    /**
     * 是否为次要菜单
     * <p>
     * 该属性将调整菜单外观以不再强调菜单的内容
     */
    private boolean secondary;

    /**
     * 是否为带指针菜单
     * <p>
     * 指针菜单可以指向相邻的元素以显示和相邻元素的关系
     */
    private boolean pointing;

    /**
     * 是否为标签菜单
     * <p>
     * 该属性将菜单调整为标签菜单样式
     */
    private boolean tabular;

    /**
     * 是否为垂直菜单
     * <p>
     * 该属性会将菜单垂直显示
     */
    private boolean vertical;

    /**
     * 是否为固定菜单
     * <p>
     * 固定菜单将不会随滚动条滚动
     */
    private boolean fixed;

    /**
     * 固定方向
     * <p>
     * 若固定方向为左右，应该使用垂直菜单
     */
    private Direction fixDirection = Direction.NONE;

    /**
     * 是否自动堆叠
     * <p>
     * 堆叠已经将变成垂直菜单
     * <p>
     * 自动堆叠的菜单应该只使用简单菜单项
     */
    private boolean stackable;

    /**
     * 是否翻转颜色
     */
    private boolean inverted;

    /**
     * 菜单颜色
     * <p>
     * 仅渲染在激活菜单项上面
     */
    private Color color = Color.NONE;

    /**
     * 是否为图标菜单
     */
    private boolean icon;

    @Singular
    private List<MenuItem> items = Lists.newArrayList();

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui menu");

        if (text) {
            stringBuilder.append(" text");
        }

        if (secondary) {
            stringBuilder.append(" secondary");
        }

        if (pointing) {
            stringBuilder.append(" pointing");
        }

        if (tabular) {
            stringBuilder.append(" tabular");
        }

        if (vertical) {
            stringBuilder.append(" vertical");
        }

        return stringBuilder.toString();
    }
}
