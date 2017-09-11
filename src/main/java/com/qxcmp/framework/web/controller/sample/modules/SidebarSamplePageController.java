package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.item.HeaderItem;
import com.qxcmp.framework.web.view.elements.menu.item.ImageItem;
import com.qxcmp.framework.web.view.elements.menu.item.LabeledIconItem;
import com.qxcmp.framework.web.view.elements.menu.item.TextItem;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.sidebar.MenuSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.Sidebar;
import com.qxcmp.framework.web.view.modules.sidebar.SidebarConfig;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Fixed;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/sidebar")
public class SidebarSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new MenuSidebar().setAttachEventsSelector(".ui.menu img").setConfig(SidebarConfig.builder().dimPage(false).build())
                .setTopFixedMenu(new Menu().setInverted().setFixed(Fixed.TOP).addItem(new ImageItem(qxcmpConfiguration.getLogo())).addItem(new HeaderItem(qxcmpConfiguration.getTitle())))
                .setBottomFixedMenu(new Menu().setInverted().setFixed(Fixed.BOTTOM).addItem(new TextItem("关于我们", "/test/sample/sidebar")).setRightMenu((RightMenu) new RightMenu().addItem(new TextItem("法律声明", "/"))))
                .addSideContent(new LabeledIconItem("首页", "home").setAnchor(new Anchor("", "/test/sample/sidebar")))
                .addSideContent(new LabeledIconItem("主题", "block layout").setAnchor(new Anchor("", "/test/sample/sidebar")))
                .addSideContent(new LabeledIconItem("朋友", "smile").setAnchor(new Anchor("", "/test/sample/sidebar")))
                .addContent(new VerticallyDividedGrid().setContainer().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                        .addItem(new Col().addComponent(new Segment()
                                .addComponent(new PageHeader(HeaderType.H1, "侧边导航栏"))
                                .addComponent(new P("点击上方LOGO打开侧边栏"))
                        ))));
    }


}
