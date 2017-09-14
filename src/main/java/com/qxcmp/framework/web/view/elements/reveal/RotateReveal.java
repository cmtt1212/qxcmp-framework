package com.qxcmp.framework.web.view.elements.reveal;

import com.qxcmp.framework.web.view.Component;

public class RotateReveal extends AbstractReveal {
    public RotateReveal(Component visibleContent, Component hiddenContent) {
        super(visibleContent, hiddenContent);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " rotate";
    }
}
