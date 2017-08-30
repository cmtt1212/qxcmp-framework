package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.QXCMPComponent;

public abstract class AbstractSegment extends QXCMPComponent {

    private static final String TEMPLATE_FILE = "qxcmp/containers/segment";

    public AbstractSegment() {
        super(TEMPLATE_FILE);
    }

    public AbstractSegment(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
