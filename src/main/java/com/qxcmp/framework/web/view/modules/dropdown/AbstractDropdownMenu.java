package com.qxcmp.framework.web.view.modules.dropdown;

import com.qxcmp.framework.web.view.AbstractComponent;

/**
 * 下拉框菜单
 *
 * @author Aaric
 */
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
