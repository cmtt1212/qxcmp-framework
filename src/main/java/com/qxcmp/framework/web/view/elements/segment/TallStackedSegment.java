package com.qxcmp.framework.web.view.elements.segment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 高堆叠样式片段
 *
 * @author aaric
 * @see StackedSegment
 */
public class TallStackedSegment extends StackedSegment {
    @Override
    public String getClassContent() {
        return super.getClassContent() + " tall";
    }
}
