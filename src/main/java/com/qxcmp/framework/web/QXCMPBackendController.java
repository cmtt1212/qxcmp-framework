package com.qxcmp.framework.web;

import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.model.navigation.NavigationService;
import com.qxcmp.framework.web.view.BackendPage;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.VerticalSubMenu;
import com.qxcmp.framework.web.view.elements.menu.item.*;
import com.qxcmp.framework.web.view.modules.accordion.AccordionItem;
import com.qxcmp.framework.web.view.support.Fixed;
import org.springframework.beans.factory.annotation.Autowired;

import static com.qxcmp.framework.web.QXCMPWebConfiguration.NAVIGATION_QXCMP_ADMIN_SIDEBAR;

public abstract class QXCMPBackendController extends QXCMPController {

    private NavigationService navigationService;

    @Override
    protected BackendPage page() {
        BackendPage page = applicationContext.getBean(BackendPage.class, request, response);
        page.setTopMenu(new Menu().setInverted().setFixed(Fixed.TOP).addItem(new LogoImageItem(qxcmpConfiguration.getLogo())).addItem(new HeaderItem(qxcmpConfiguration.getTitle())));
        page.setBottomMenu(new Menu().setInverted().setFixed(Fixed.BOTTOM).addItem(new SidebarIconItem()).addItem(new TextItem("关于我们", "/test/sample/sidebar")).setRightMenu((RightMenu) new RightMenu().addItem(new TextItem("法律声明", "/"))));
        addPageSidebarContent(page, currentUser().orElse(null));
        return page;
    }

    private void addPageSidebarContent(BackendPage page, User user) {
        navigationService.get(NAVIGATION_QXCMP_ADMIN_SIDEBAR).getItems().forEach(navigation -> {
            if (navigation.isVisible(user)) {
                if (navigation.getItems().isEmpty()) {
                    page.addSideContent(new TextItem(navigation.getTitle(), navigation.getAnchor().getHref()));
                } else {
                    if (navigation.getItems().stream().anyMatch(n -> n.isVisible(user))) {

                        VerticalSubMenu verticalMenu = new VerticalSubMenu();

                        navigation.getItems().forEach(item -> {
                            if (item.isVisible(user)) {
                                verticalMenu.addItem(new TextItem(item.getTitle(), item.getAnchor().getHref()));
                            }
                        });

                        AccordionItem accordionItem = new AccordionItem();
                        accordionItem.setTitle(navigation.getTitle());
                        accordionItem.setContent(verticalMenu);

                        AccordionMenuItem accordionMenuItem = new AccordionMenuItem(accordionItem);
                        page.addSideContent(accordionMenuItem);
                    }
                }
            }
        });
    }

    @Autowired
    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }
}
