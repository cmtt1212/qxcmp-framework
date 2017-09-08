package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.item.ButtonItem;
import com.qxcmp.framework.web.view.elements.menu.item.HeaderItem;
import com.qxcmp.framework.web.view.elements.menu.item.ImageItem;
import com.qxcmp.framework.web.view.elements.menu.item.TextItem;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
                .addItem(new ImageItem(qxcmpConfiguration.getLogo()))
                .addItem(new HeaderItem(qxcmpConfiguration.getTitle()))
                .addItem(new TextItem("首页", "/test/sample").setActive())
                .addItem(new TextItem("关于", "/test/sample"))
                ;
    }

    private Component createPointingMenu() {
        return new Menu().setPointing()
                .addItem(new ImageItem(qxcmpConfiguration.getLogo()))
                .addItem(new HeaderItem(qxcmpConfiguration.getTitle()))
                .addItem(new TextItem("首页", "/test/sample").setActive())
                .addItem(new TextItem("关于", "/test/sample"))
                ;
    }

    private Component createSecondaryMenu() {
        return new Menu().setSecondary()
                .addItem(new ImageItem(qxcmpConfiguration.getLogo()))
                .addItem(new HeaderItem(qxcmpConfiguration.getTitle()))
                .addItem(new TextItem("首页", "/test/sample").setActive())
                .addItem(new TextItem("关于", "/test/sample"))
                ;
    }

    private Component createStandardMenu() {
        return new Menu()
                .addItem(new ImageItem(qxcmpConfiguration.getLogo()))
                .addItem(new HeaderItem(qxcmpConfiguration.getTitle()))
                .addItem(new TextItem("首页", "/test/sample").setActive())
                .addItem(new TextItem("关于", "/test/sample"))
                .setRightMenu((RightMenu) new RightMenu().addItem(new ButtonItem(new Button("注册").setBasic().setColor(randomColor()))))
                ;
    }


}
