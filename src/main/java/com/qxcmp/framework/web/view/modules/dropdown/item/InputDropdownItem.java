package com.qxcmp.framework.web.view.modules.dropdown.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InputDropdownItem extends AbstractItem {

    /**
     * 输入框 name - 用于表单提交
     */
    private String name;

    /**
     * 输入框图标名称
     * <p>
     * 图标默认显示在左侧
     */
    private String icon = "search";

    /**
     * 图标是否右侧显示
     */
    private boolean rightIcon;

    /**
     * 输入框默认文本
     */
    private String placeholder;

    @Override
    public String getFragmentName() {
        return "item-input";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui");

        if (rightIcon) {
            stringBuilder.append(" right icon");
        } else {
            stringBuilder.append(" left icon");
        }

        stringBuilder.append(" input");

        return stringBuilder.toString();
    }
}
