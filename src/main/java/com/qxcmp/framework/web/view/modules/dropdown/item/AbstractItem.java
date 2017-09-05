package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractItem extends AbstractComponent {

    /**
     * 是否为禁用状态
     */
    private boolean disabled;

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/dropdown";
    }
}
