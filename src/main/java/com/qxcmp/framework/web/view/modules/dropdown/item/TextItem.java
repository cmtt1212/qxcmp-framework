package com.qxcmp.framework.web.view.modules.dropdown.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextItem extends AbstractTextDropdownItem implements DropdownItem, SelectionItem {

    public TextItem(String text) {
        super(text);
    }

    @Override
    public String getFragmentName() {
        return "item-text";
    }
}
