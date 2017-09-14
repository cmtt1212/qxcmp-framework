package com.qxcmp.framework.web.view.elements.reveal;

import com.qxcmp.framework.web.view.Component;

public class MoveDownReveal extends MoveReveal {
    public MoveDownReveal(Component visibleContent, Component hiddenContent) {
        super(visibleContent, hiddenContent);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " down";
    }
}
