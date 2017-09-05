package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.Direction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDropdownItem extends AbstractTextDropdownItem {

    /**
     * 子菜单打开方向，只支持 LEFT, RIGHT
     */
    private Direction direction = Direction.NONE;

    /**
     * 子选项
     */
    private List<AbstractDropdownItem> items = Lists.newArrayList();

    @Override
    public String getFragmentName() {
        return "item-menu";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        stringBuilder.append(" item");

        return stringBuilder.toString();
    }
}
