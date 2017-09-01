package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.web.view.elements.menu.AbstractMenu;
import com.qxcmp.framework.web.view.support.Color;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractMenuItem extends AbstractMenu implements MenuItem {

    /**
     * 菜单项颜色
     */
    private Color color = Color.NONE;

    /**
     *
     */
    private boolean fitted;
}
