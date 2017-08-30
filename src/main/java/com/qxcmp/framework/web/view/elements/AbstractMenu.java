package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;

public abstract class AbstractMenu implements Component {
    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/menu";
    }
}
