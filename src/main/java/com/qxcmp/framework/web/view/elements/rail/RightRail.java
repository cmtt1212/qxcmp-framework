package com.qxcmp.framework.web.view.elements.rail;

import com.qxcmp.framework.web.view.Component;

public class RightRail extends AbstractRail {
    public RightRail(Component component) {
        super(component);
    }

    @Override
    public String getClassSuffix() {
        return "right rail";
    }
}
