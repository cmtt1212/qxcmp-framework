package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.breadcrumb.Breadcrumb;
import com.qxcmp.framework.web.view.elements.breadcrumb.BreadcrumbItem;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.menu.Menu;
import com.qxcmp.framework.web.view.elements.menu.RightMenu;
import com.qxcmp.framework.web.view.elements.menu.VerticalSubMenu;
import com.qxcmp.framework.web.view.elements.menu.item.AccordionMenuItem;
import com.qxcmp.framework.web.view.elements.menu.item.LogoImageItem;
import com.qxcmp.framework.web.view.elements.menu.item.SidebarIconItem;
import com.qxcmp.framework.web.view.elements.menu.item.TextItem;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.accordion.AccordionItem;
import com.qxcmp.framework.web.view.modules.sidebar.AccordionMenuSidebar;
import com.qxcmp.framework.web.view.modules.sidebar.SidebarConfig;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Fixed;
import com.qxcmp.framework.web.view.support.Width;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/sidebar")
public class SidebarSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page().addComponent(new AccordionMenuSidebar().setAttachEventsSelector(".ui.bottom.fixed.menu .sidebar.item").setConfig(SidebarConfig.builder().dimPage(false).build()).setWidth(Width.THIN)
                .setTopFixedMenu(new Menu().setInverted().setFixed(Fixed.TOP).addItem(new LogoImageItem(siteService.getLogo(), siteService.getTitle())))
                .setBottomFixedMenu(new Menu().setInverted().setFixed(Fixed.BOTTOM).addItem(new SidebarIconItem()).addItem(new TextItem("关于我们", "/test/sample/sidebar")).setRightMenu((RightMenu) new RightMenu().addItem(new TextItem("法律声明", "/"))))
                .addSideContent(new TextItem("内容管理", "/test/sample/sidebar"))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addSideContent(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")).addItem(new TextItem("网站设置", "/")))))
                .addContent(new VerticallyDividedGrid().setContainer().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                        .addItem(new Col().addComponent(new Breadcrumb().addItem(new BreadcrumbItem("首页")).addItem(new BreadcrumbItem("侧边栏").setActive())))
                        .addItem(new Col().addComponent(new Segment()
                                .addComponent(new PageHeader(HeaderType.H1, "页面内容"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                                .addComponent(new Image("/assets/images/placeholder-paragraph.png"))
                        )))).build();
    }


}
