package com.qxcmp.framework.web.page;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.elements.icon.CircularIcon;
import com.qxcmp.framework.web.view.elements.menu.AbstractMenu;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.item.IconItem;
import com.qxcmp.framework.web.view.modules.sidebar.AbstractSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.AccordionMenuSidebar;
import com.qxcmp.framework.web.view.support.Fixed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MobilePage extends AbstractFrontendPage {

    private AbstractSidebar sidebar = new AccordionMenuSidebar().setAttachEventsSelector(".ui.top.fixed.menu .user.icon");

    public MobilePage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     * 为侧边导航栏增加内容
     * <p>
     *
     * @param component
     *
     * @return
     */
    public MobilePage addSideContent(AbstractComponent component) {
        sidebar.addSideContent(component);
        return this;
    }

    @Override
    public MobilePage addComponent(com.qxcmp.framework.web.view.Component component) {
        sidebar.addContent(component);
        return this;
    }

    @Override
    public MobilePage addComponents(Collection<? extends com.qxcmp.framework.web.view.Component> components) {
        sidebar.addContents(components);
        return this;
    }

    @Override
    public ModelAndView build() {
        sidebar.setTopFixedMenu(getTopMenu());
        super.addComponent(sidebar);
        return super.build();
    }

    private AbstractMenu getTopMenu() {
        final AbstractMenu menu = new Menu().setFixed(Fixed.TOP).setInverted();

        menu.addItem(new IconItem(new CircularIcon("user")));

        return menu;
    }
}
