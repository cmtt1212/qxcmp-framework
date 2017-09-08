package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.web.view.elements.icon.Icon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabeledIconItem extends AbstractMenuItem {

    private String label;

    private Icon icon;

    public LabeledIconItem(String label, Icon icon) {
        this.label = label;
        this.icon = icon;
    }

    public LabeledIconItem(String label, String iconName) {
        this.label = label;
        this.icon = new Icon(iconName);
    }

    @Override
    public String getFragmentName() {
        return "item-labeled-icon";
    }
}
