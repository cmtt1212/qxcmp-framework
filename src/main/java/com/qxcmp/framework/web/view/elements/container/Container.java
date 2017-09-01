package com.qxcmp.framework.web.view.elements.container;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 标准容器
 * <p>
 * 标准容器会根据屏幕宽度自动调整自身宽度
 *
 * @author aaric
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Container extends AbstractContainer {

}
