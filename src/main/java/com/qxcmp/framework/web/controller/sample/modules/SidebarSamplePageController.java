package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.elements.menu.item.LabeledIconItem;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.sidebar.LabeledIconMenuSidebar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/sidebar")
public class SidebarSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new LabeledIconMenuSidebar().setAttachEventsSelector("button")
                .addSideContent(new LabeledIconItem("首页", "home").setAnchor(new Anchor("", "/test/sample/sidebar")))
                .addSideContent(new LabeledIconItem("主题", "block layout").setAnchor(new Anchor("", "/test/sample/sidebar")))
                .addSideContent(new LabeledIconItem("朋友", "smile").setAnchor(new Anchor("", "/test/sample/sidebar")))
                .addContent(new Container().addComponent(new Segment()
                        .addComponent(new PageHeader(HeaderType.H1, "侧边导航栏"))
                        .addComponent(new Button("打开"))
                )));
    }


}
