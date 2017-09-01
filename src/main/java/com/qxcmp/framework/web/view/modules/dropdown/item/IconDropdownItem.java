package com.qxcmp.framework.web.view.modules.dropdown.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class IconDropdownItem extends AbstractSelectionItem {

    /**
     * 选项图标名称
     */
    private String icon;

    @Override
    public String getFragmentName() {
        return "item-icon";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        stringBuilder.append(" item");

        return stringBuilder.toString();
    }
}
