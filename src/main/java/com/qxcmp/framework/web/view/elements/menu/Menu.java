package com.qxcmp.framework.web.view.elements.menu;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
     * 是否为附着菜单
     */
    private boolean attached;

    /**
     * 附着方向，当为附着菜单的时候生效，方向仅支持 TOP, BOTTOM, NONE
     */
    private Direction attachDirection = Direction.NONE;

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

        if (attached) {
            if (StringUtils.isNotBlank(attachDirection.getClassName())) {
                stringBuilder.append(" ").append(attachDirection.getClassName());
            }
            stringBuilder.append(" attached");
        }

        return stringBuilder.toString();
    }
}
