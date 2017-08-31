package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MenuDropdownItem extends AbstractItem {

    /**
     * 子选项
     */
    private List<AbstractItem> items = Lists.newArrayList();

    @Override
    public String getFragmentName() {
        return "item-menu";
    }
}
