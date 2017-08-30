package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.QXCMPComponent;

public class AbstractGrid extends QXCMPComponent {

    private static final String TEMPLATE_FILE = "qxcmp/containers/grid";

    public AbstractGrid() {
        super(TEMPLATE_FILE);
    }

    public AbstractGrid(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
