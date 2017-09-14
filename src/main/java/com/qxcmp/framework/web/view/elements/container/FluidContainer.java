package com.qxcmp.framework.web.view.elements.container;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 流容器
 * <p>
 * 该容器会占满父容器宽度
 *
 * @author aaric
 */
public class FluidContainer extends AbstractContainer {

    @Override
    public String getClassContent() {
        return super.getClassContent() + " fluid";
    }
}
