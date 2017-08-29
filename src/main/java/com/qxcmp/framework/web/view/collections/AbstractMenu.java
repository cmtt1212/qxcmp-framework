package com.qxcmp.framework.web.view.collections;

import com.qxcmp.framework.web.view.Component;

public abstract class AbstractMenu implements Component {
    @Override
    public String getFragmentFile() {
        return "qxcmp/collections/menu";
    }
}
