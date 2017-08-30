package com.qxcmp.framework.web.view.elements.menu;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMenu extends QXCMPComponent {

    private static final String TEMPLATE_FILE = "qxcmp/elements/menu";

    public AbstractMenu() {
        super(TEMPLATE_FILE);
    }

    public AbstractMenu(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
