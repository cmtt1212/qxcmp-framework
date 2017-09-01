package com.qxcmp.framework.web.view.elements.segment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class BasicSegment extends Segment {
    @Override
    public String getClassContent() {
        return super.getClassContent() + " basic";
    }
}
