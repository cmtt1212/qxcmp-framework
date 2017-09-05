package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

/**
 * 下拉框菜单
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractDropdownMenu extends AbstractComponent {

    @Override
    public String getFragmentFile() {
        return "qxcmp/modules/dropdown";
    }

    @Override
    public String getFragmentName() {
        return "menu";
    }
}
