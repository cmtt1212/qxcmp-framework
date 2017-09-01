package com.qxcmp.framework.web.view.modules.dropdown.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class DividerDropdownItem extends AbstractItem {

    @Override
    public String getFragmentName() {
        return "item-divider";
    }
}
