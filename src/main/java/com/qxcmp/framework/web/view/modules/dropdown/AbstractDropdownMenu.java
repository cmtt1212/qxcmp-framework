package com.qxcmp.framework.web.view.modules.dropdown;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.modules.dropdown.item.AbstractDropdownItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @Override
    public String getClassSuffix() {
        return "menu";
    }
}
