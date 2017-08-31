package com.qxcmp.framework.web.view.modules.dropdown;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.modules.dropdown.item.AbstractItem;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单下拉框
 * <p>
 * 用于嵌入在菜单中使用
 *
 * @author Aaric
 */
@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MenuDropdown extends AbstractDropdown {

    /**
     * 菜单文本
     */
    private String text;

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
        return "menu";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (pointing) {
            if (StringUtils.isNotBlank(pointingDirection.getClassName())) {
                stringBuilder.append(" ").append(pointingDirection.getClassName());
            }
            stringBuilder.append(" pointing");
        }

        stringBuilder.append(" dropdown");

        return stringBuilder.toString();
    }
}
