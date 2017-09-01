package com.qxcmp.framework.web.view.elements.container;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 文本容器
 * <p>
 * 文本容器会比标准容器具有更窄的宽度
 *
 * @author aaric
 */
public class TextContainer extends AbstractContainer {

    @Override
    public String getClassContent() {
        return super.getClassContent() + " text";
    }
}
