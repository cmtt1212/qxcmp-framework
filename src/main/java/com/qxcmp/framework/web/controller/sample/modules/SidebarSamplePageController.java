package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.menu.VerticalIconMenu;
import com.qxcmp.framework.web.view.elements.menu.item.IconItem;
import com.qxcmp.framework.web.view.modules.sidebar.Sidebar;
import com.qxcmp.framework.web.view.modules.sidebar.SidebarConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/sidebar")
public class SidebarSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Sidebar().setAttachEventsSelector("button")
                .setConfig(SidebarConfig.builder().dimPage(false).build())
                .addSideContent(new VerticalIconMenu().addItem(new IconItem("user")))
                .addContent(new PageHeader(HeaderType.H1, "侧边栏"))
                .addContent(new Button("打开侧边栏"))
        );
    }


}
