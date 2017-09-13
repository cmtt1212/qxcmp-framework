package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.html.P;
import com.qxcmp.framework.web.view.elements.menu.AccordionMenu;
import com.qxcmp.framework.web.view.elements.menu.VerticalSubMenu;
import com.qxcmp.framework.web.view.elements.menu.item.AccordionMenuItem;
import com.qxcmp.framework.web.view.elements.menu.item.TextItem;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.accordion.Accordion;
import com.qxcmp.framework.web.view.modules.accordion.AccordionItem;
import com.qxcmp.framework.web.view.modules.accordion.StyledAccordion;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/accordion")
public class AccordionSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page().addComponent(new VerticallyDividedGrid().setContainer().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(createAccordionSegment()))
                .addItem(new Col().addComponent(createStyledAccordionSegment()))
                .addItem(new Col().addComponent(createAccordionMenuSegment()))
        ).build();
    }

    private Component createAccordionMenuSegment() {
        return new Segment().addComponent(new ContentHeader("菜单手风琴", Size.LARGE).setDividing())
                .addComponent(new AccordionMenu()
                        .addItem(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("内容管理").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/test/sample")).addItem(new TextItem("网站设置", "/test/sample")).addItem(new TextItem("网站设置", "/test/sample")))))
                        .addItem(new AccordionMenuItem((AccordionItem) new AccordionItem().setTitle("系统设置").setContent(new VerticalSubMenu().addItem(new TextItem("网站设置", "/test/sample")).addItem(new TextItem("网站设置", "/test/sample")).addItem(new TextItem("网站设置", "/test/sample")))))
                );
    }

    private Component createStyledAccordionSegment() {
        return new Segment().addComponent(new ContentHeader("带样式的手风琴", Size.LARGE).setDividing())
                .addComponent(new StyledAccordion()
                        .addItem(new AccordionItem().setTitle("什么是手风琴").setContent(new P("手风琴是...")))
                        .addItem(new AccordionItem().setActive().setTitle("什么是手风琴").setContent(new P("手风琴是...")))
                        .addItem(new AccordionItem().setTitle("什么是手风琴").setContent(new P("手风琴是...")))
                );
    }

    private Component createAccordionSegment() {
        return new Segment().addComponent(new ContentHeader("标准手风琴", Size.LARGE).setDividing())
                .addComponent(new Accordion().setExclusive()
                        .addItem(new AccordionItem().setActive().setTitle("什么是手风琴").setContent(new P("手风琴是...")))
                        .addItem(new AccordionItem().setTitle("什么是手风琴").setContent(new P("手风琴是...")))
                        .addItem(new AccordionItem().setTitle("什么是手风琴").setContent(new P("手风琴是...")))
                );
    }


}
