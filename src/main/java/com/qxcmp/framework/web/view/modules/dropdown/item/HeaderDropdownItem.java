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
public class HeaderDropdownItem extends AbstractItem {

    /**
     * 标题图标名称
     */
    private String icon;

    @Override
    public String getFragmentName() {
        return "item-header";
    }
}
