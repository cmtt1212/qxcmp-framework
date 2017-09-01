package com.qxcmp.framework.web.view.elements.segment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 垂直片段
 *
 * @author aaric
 * @see Segment
 */
public class VerticalSegment extends Segment {
    @Override
    public String getClassContent() {
        return super.getClassContent() + " vertical";
    }
}
