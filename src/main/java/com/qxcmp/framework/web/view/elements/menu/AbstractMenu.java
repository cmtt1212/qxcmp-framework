package com.qxcmp.framework.web.view.elements.menu;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMenu extends AbstractComponent {

    private static final String TEMPLATE_FILE = "qxcmp/elements/menu";

    public AbstractMenu() {
        super(TEMPLATE_FILE);
    }

    public AbstractMenu(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
