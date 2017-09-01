package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractItem extends AbstractComponent {

    public AbstractItem() {
        super("qxcmp/modules/dropdown");
    }
}
