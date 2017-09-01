package com.qxcmp.framework.web.view.elements.segment;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Attached;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * 片段一般用来创建一组相关的内容
 * <p>
 * 一个片段包含一定的内容
 *
 * @author aaric
 */
@Getter
@Setter
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Segment extends AbstractSegment {

    /**
     * 是否翻转颜色
     */
    private boolean inverted;

    /**
     * 附着方式
     * <p>
     * 附着方式可与其他的同样拥有附着属性的组件结合使用
     */
    private Attached attached = Attached.NONE;

    /**
     * 片段内容
     */
    private List<Component> components = Lists.newArrayList();

}
