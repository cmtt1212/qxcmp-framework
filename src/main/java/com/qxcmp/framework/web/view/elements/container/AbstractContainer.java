package com.qxcmp.framework.web.view.elements.container;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Alignment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AbstractContainer extends AbstractComponent {

    /**
     * 容器子元素对齐方式
     */
    private Alignment alignment = Alignment.NONE;

    /**
     * 容器内容
     */
    private List<Component> components = Lists.newArrayList();

    public AbstractContainer() {
        super("qxcmp/elements/container");
    }

    @Override
    public String getClassPrefix() {
        return "ui";
    }

    @Override
    public String getClassContent() {
        return alignment.toString();
    }

    @Override
    public String getClassSuffix() {
        return "container";
    }
}
