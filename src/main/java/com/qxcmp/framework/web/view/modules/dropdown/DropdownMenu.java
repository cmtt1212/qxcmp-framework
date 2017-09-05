package com.qxcmp.framework.web.view.modules.dropdown;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.modules.dropdown.item.DropdownItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DropdownMenu extends AbstractDropdownMenu {

    private List<DropdownItem> items = Lists.newArrayList();

    public DropdownMenu addItem(DropdownItem item) {
        items.add(item);
        return this;
    }
}
