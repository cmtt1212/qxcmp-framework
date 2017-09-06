package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.qxcmp.framework.web.view.elements.input.AbstractInput;
import lombok.Getter;

@Getter
public class InputItem extends AbstractDropdownItem implements DropdownItem {

    private AbstractInput input;

    public InputItem(AbstractInput input) {
        this.input = input;
    }

    @Override
    public String getFragmentName() {
        return "item-input";
    }
}
