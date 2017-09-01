package com.qxcmp.framework.web.view.elements.segment;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 堆叠样式片段
 *
 * @author aaric
 * @see Segment
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StackedSegment extends AbstractSegment {
}
