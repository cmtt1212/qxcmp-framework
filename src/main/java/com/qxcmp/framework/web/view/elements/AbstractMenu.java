package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.QXCMPComponent;

public abstract class AbstractMenu extends QXCMPComponent {

    private static final String TEMPLATE_FILE = "qxcmp/elements/menu";

    public AbstractMenu() {
        super(TEMPLATE_FILE);
    }

    public AbstractMenu(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
