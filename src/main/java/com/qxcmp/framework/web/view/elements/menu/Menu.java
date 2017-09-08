package com.qxcmp.framework.web.view.elements.menu;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Menu extends AbstractMenu {

    @Override
    public String getFragmentName() {
        return "menu";
    }
}
