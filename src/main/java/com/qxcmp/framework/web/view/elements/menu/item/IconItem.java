package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.web.view.elements.icon.Icon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconItem extends AbstractMenuItem {

    private Icon icon;

    public IconItem(String iconName) {
        this.icon = new Icon(iconName);
    }

    public IconItem(Icon icon) {
        this.icon = icon;
    }

    @Override
    public String getFragmentName() {
        return "item-icon";
    }
}
