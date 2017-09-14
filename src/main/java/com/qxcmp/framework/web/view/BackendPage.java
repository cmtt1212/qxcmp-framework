package com.qxcmp.framework.web.view;

import com.qxcmp.framework.web.view.modules.sidebar.Sidebar;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

/**
 * 后端页面
 *
 * @author Aaric
 */
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BackendPage extends AbstractPage {

    private Sidebar sidebar = new Sidebar();

    public BackendPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public BackendPage addComponent(Component component) {
        sidebar.addSideContent(component);
        return this;
    }

    @Override
    public BackendPage addComponents(Collection<? extends Component> components) {
        sidebar.addSideContents(components);
        return this;
    }

    @Override
    public ModelAndView build() {
        super.addComponent(sidebar);
        return super.build();
    }
}
