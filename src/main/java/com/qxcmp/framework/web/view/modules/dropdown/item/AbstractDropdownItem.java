package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDropdownItem extends AbstractComponent {

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/dropdown";
    }

    @Override
    public String getClassSuffix() {
        return "item";
    }
}
