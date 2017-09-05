package com.qxcmp.framework.web.view.elements.label;

public class EmptyCircularLabel extends CircularLabel {
    public EmptyCircularLabel() {
        super("");
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " empty";
    }
}
