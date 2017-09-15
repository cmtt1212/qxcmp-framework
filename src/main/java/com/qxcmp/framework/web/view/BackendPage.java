package com.qxcmp.framework.web.view;

import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.menu.AbstractMenu;
import com.qxcmp.framework.web.view.modules.sidebar.AbstractSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.AccordionMenuSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.SidebarConfig;
import com.qxcmp.framework.web.view.support.Width;
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

    private AbstractSidebar sidebar = new AccordionMenuSidebar().setAttachEventsSelector(".ui.bottom.fixed.menu .sidebar.item").setConfig(SidebarConfig.builder().dimPage(false).build()).setWidth(Width.THIN);

    private Container container = new Container();

    public BackendPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public BackendPage addComponent(Component component) {
        container.addComponent(component);
        return this;
    }

    @Override
    public BackendPage addComponents(Collection<? extends Component> components) {
        container.addComponents(components);
        return this;
    }

    public BackendPage setTopMenu(AbstractMenu menu) {
        sidebar.setTopFixedMenu(menu);
        return this;
    }

    public BackendPage setBottomMenu(AbstractMenu menu) {
        sidebar.setBottomFixedMenu(menu);
        return this;
    }

    public BackendPage addSideContent(Component content) {
        sidebar.addSideContent(content);
        return this;
    }

    @Override
    public ModelAndView build() {
        super.addComponent(sidebar.addContent(container));
        return super.build();
    }
}
