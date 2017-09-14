package com.qxcmp.framework.web.view;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 页面对象
 * <p>
 * 包含了渲染页面所有的信息
 *
 * @author aaric
 */
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrontendPage extends AbstractPage {
    public FrontendPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public FrontendPage addComponent(Component component) {
        return (FrontendPage) super.addComponent(component);
    }

    @Override
    public FrontendPage addComponents(Collection<? extends Component> components) {
        return (FrontendPage) super.addComponents(components);
    }
}
