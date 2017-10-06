package com.qxcmp.framework.web.page;

import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.model.navigation.NavigationService;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.icon.CircularIcon;
import com.qxcmp.framework.web.view.elements.menu.AbstractMenu;
import com.qxcmp.framework.web.view.elements.menu.LabeledIconMenu;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.item.IconItem;
import com.qxcmp.framework.web.view.elements.menu.item.LabeledIconItem;
import com.qxcmp.framework.web.view.elements.menu.item.TextItem;
import com.qxcmp.framework.web.view.modules.sidebar.AbstractSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.AccordionMenuSidebar;
import com.qxcmp.framework.web.view.support.Fixed;
import com.qxcmp.framework.web.view.support.ItemCount;
import com.qxcmp.framework.web.view.views.ProfileHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.*;

@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MobilePage extends BaseFrontendPage {

    private AbstractSidebar sidebar = new AccordionMenuSidebar().setAttachEventsSelector(".ui.top.fixed.menu .user.icon");

    private String activeBottomItem;
    private boolean hideBottomMenu;

    private UserService userService;
    private NavigationService navigationService;

    public MobilePage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public AbstractPage addComponent(Supplier<Component> supplier) {
        Component component = checkNotNull(supplier.get(), "Component is null");
        sidebar.addContent(component);
        return this;
    }

    @Override
    public AbstractPage addComponent(Component component) {
        sidebar.addContent(component);
        return this;
    }

    @Override
    public AbstractPage addComponents(Collection<? extends Component> components) {
        sidebar.addContents(components);
        return this;
    }

    @Override
    public AbstractPage setMobileBottomMenuActiveItem(String id) {
        this.activeBottomItem = id;
        return this;
    }

    @Override
    public AbstractPage hideMobileBottomMenu() {
        hideBottomMenu = true;
        return this;
    }

    @Override
    public ModelAndView build() {

        buildSidebarMenu();

        super.addComponent(sidebar);
        return super.build();
    }

    private void buildSidebarMenu() {
        final User user = userService.currentUser();

        buildSidebarTopFixedMenu(user);

        if (!hideBottomMenu) {
            buildSidebarBottomFixedMenu(user);
        }

        buildSidebarContent(user);

        sidebar.setCustomClass("mobile");
    }

    private void buildSidebarTopFixedMenu(User user) {
        final AbstractMenu menu = new Menu();
        menu.setInverted().setFixed(Fixed.TOP);
        menu.addItem(new IconItem(new CircularIcon("user")));
        RightMenu rightMenu = new RightMenu();

        navigationService.get(NAVIGATION_GLOBAL_MOBILE_TOP).getItems().stream().filter(navigation -> navigation.isVisible(user)).forEach(navigation -> {
            if (Objects.isNull(navigation.getIcon())) {
                rightMenu.addItem(new TextItem(navigation.getTitle(), navigation.getAnchor().getHref()));
            } else {
                rightMenu.addItem(new IconItem(navigation.getIcon(), navigation.getAnchor()));
            }
        });

        if (!rightMenu.getItems().isEmpty()) {
            menu.setRightMenu(rightMenu);
        }

        sidebar.setTopFixedMenu(menu);
    }

    private void buildSidebarBottomFixedMenu(User user) {
        final AbstractMenu menu = new LabeledIconMenu();
        menu.setInverted().setFixed(Fixed.BOTTOM);

        navigationService.get(NAVIGATION_GLOBAL_MOBILE_BOTTOM).getItems().stream().filter(navigation -> navigation.isVisible(user)).forEach(navigation -> {
            LabeledIconItem item = new LabeledIconItem(navigation.getTitle(), navigation.getIcon(), navigation.getAnchor());

            if (StringUtils.equals(activeBottomItem, navigation.getId())) {
                item.setActive(true);
            }

            menu.addItem(item);
        });

        menu.setItemCount(ItemCount.values()[menu.getItems().size()]);

        sidebar.setBottomFixedMenu(menu);
    }

    private void buildSidebarContent(User user) {
        if (Objects.nonNull(user)) {
            sidebar.addSideContent(new ProfileHeader(user));
        }

        navigationService.get(NAVIGATION_GLOBAL_MOBILE_SIDEBAR).getItems().stream().filter(navigation -> navigation.isVisible(user)).forEach(navigation -> {
            if (Objects.isNull(navigation.getIcon())) {
                sidebar.addSideContent(new TextItem(navigation.getTitle(), navigation.getAnchor().getHref()));
            } else {
                sidebar.addSideContent(new LabeledIconItem(navigation.getTitle(), navigation.getIcon(), navigation.getAnchor()));
            }
        });
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }
}
