package com.qxcmp.framework.web.view.elements.menu.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderItem extends AbstractMenuItem {

    private String text;

    public HeaderItem(String text) {
        this.text = text;
    }

    @Override
    public String getFragmentName() {
        return "item-header";
    }

    @Override
    public String getClassSuffix() {
        return "header " + super.getClassSuffix();
    }
}
