package com.qxcmp.framework.web;

import com.qxcmp.framework.web.model.navigation.NavigationService;
import com.qxcmp.framework.web.view.BackendPage;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.item.HeaderItem;
import com.qxcmp.framework.web.view.elements.menu.item.LogoImageItem;
import com.qxcmp.framework.web.view.elements.menu.item.SidebarIconItem;
import com.qxcmp.framework.web.view.elements.menu.item.TextItem;
import com.qxcmp.framework.web.view.support.Fixed;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class QXCMPBackendController extends QXCMPController {

    private NavigationService navigationService;

    @Override
    protected BackendPage page() {
        BackendPage page = applicationContext.getBean(BackendPage.class, request, response);
        page.setTopMenu(new Menu().setInverted().setFixed(Fixed.TOP).addItem(new LogoImageItem(qxcmpConfiguration.getLogo())).addItem(new HeaderItem(qxcmpConfiguration.getTitle())));
        page.setBottomMenu(new Menu().setInverted().setFixed(Fixed.BOTTOM).addItem(new SidebarIconItem()).addItem(new TextItem("关于我们", "/test/sample/sidebar")).setRightMenu((RightMenu) new RightMenu().addItem(new TextItem("法律声明", "/"))));
        
        return page;
    }

    @Autowired
    public void setNavigationService(NavigationService navigationService) {
        this.navigationService = navigationService;
    }
}
