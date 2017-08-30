package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.Component;

public abstract class AbstractSegment implements Component {
    @Override
    public String getFragmentFile() {
        return "qxcmp/containers/segment";
    }
}
