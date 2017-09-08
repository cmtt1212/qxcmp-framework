package com.qxcmp.framework.web.controller.sample.elements;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.item.*;
import com.qxcmp.framework.web.view.modules.dropdown.DropdownMenu;
import com.qxcmp.framework.web.view.modules.dropdown.MenuDropdown;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.DropdownPointing;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/test/sample/menu")
public class MenuSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent((new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(createStandardMenu()))
                .addItem(new Col().addComponent(createSecondaryMenu()))
                .addItem(new Col().addComponent(createPointingMenu()))
                .addItem(new Col().addComponent(createSecondaryPointingMenu()))
        )));
    }

    private Component createSecondaryPointingMenu() {
        return new Menu().setSecondary().setPointing()
                .addItems(getItems()).setRightMenu(getRightMenu());
    }

    private Component createPointingMenu() {
        return new Menu().setPointing()
                .addItems(getItems()).setRightMenu(getRightMenu());
    }

    private Component createSecondaryMenu() {
        return new Menu().setSecondary()
                .addItems(getItems()).setRightMenu(getRightMenu());
    }

    private Component createStandardMenu() {
        return new Menu()
                .addItems(getItems()).setRightMenu(getRightMenu());
    }

    private Collection<? extends AbstractMenuItem> getItems() {
        List<AbstractMenuItem> menuItems = Lists.newArrayList();
        menuItems.add(new ImageItem(qxcmpConfiguration.getLogo()).setColor(randomColor()));
        menuItems.add(new HeaderItem(qxcmpConfiguration.getTitle()).setColor(randomColor()));
        menuItems.add(new TextItem("首页", "/test/sample").setActive().setColor(randomColor()));
        menuItems.add(new DropdownItem(new MenuDropdown("产品中心").setFloating().setPointing(DropdownPointing.DEFAULT).setMenu(new DropdownMenu().addItem(new com.qxcmp.framework.web.view.modules.dropdown.item.TextItem("产品一")))).setColor(randomColor()));
        menuItems.add(new TextItem("关于我们", "/test/sample").setColor(randomColor()));
        return menuItems;
    }

    private RightMenu getRightMenu() {
        return (RightMenu) new RightMenu().setRightMenu((RightMenu) new RightMenu()
                .addItem(new SearchInputItem())
                .addItem(new ButtonItem(new Button("注册", "/login").setBasic().setColor(randomColor())))
        );
    }

}
