package com.qxcmp.framework.web.view;

import com.qxcmp.framework.web.view.elements.breadcrumb.AbstractBreadcrumb;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.menu.AbstractMenu;
import com.qxcmp.framework.web.view.elements.menu.VerticalMenu;
import com.qxcmp.framework.web.view.modules.sidebar.AbstractSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.AccordionMenuSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.SidebarConfig;
import com.qxcmp.framework.web.view.support.Wide;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Objects;

/**
 * 后端页面
 *
 * @author Aaric
 */
@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BackendPage extends AbstractPage {

    private AbstractSidebar sidebar = new AccordionMenuSidebar().setAttachEventsSelector(".ui.bottom.fixed.menu .sidebar.item").setConfig(SidebarConfig.builder().dimPage(false).build());

    /**
     * 页面容器
     */
    private Col container = new Col();

    /**
     * 面包屑 - 可选
     */
    private AbstractBreadcrumb breadcrumb;

    /**
     * 垂直菜单 - 可选
     */
    private VerticalMenu verticalMenu;

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

    public BackendPage setBreadcrumb(AbstractBreadcrumb breadcrumb) {
        this.breadcrumb = breadcrumb;
        return this;
    }

    public BackendPage setVerticalMenu(VerticalMenu verticalMenu) {
        this.verticalMenu = verticalMenu;
        return this;
    }

    @Override
    public ModelAndView build() {
        final Grid grid = new Grid();
        final Row row = new Row();

        if (Objects.nonNull(breadcrumb)) {
            grid.addItem(new Row().addCol(new Col().setGeneralWide(Wide.SIXTEEN).addComponent(breadcrumb)));
        }

        if (Objects.nonNull(verticalMenu)) {
            row.addCol(new Col().setGeneralWide(Wide.FOUR).addComponent(verticalMenu));
            row.addCol(container.setGeneralWide(Wide.TWELVE));
        } else {
            row.addCol(container.setGeneralWide(Wide.SIXTEEN));
        }

        grid.addItem(row);

        super.addComponent(sidebar.addContent(grid));
        return super.build();
    }
}
