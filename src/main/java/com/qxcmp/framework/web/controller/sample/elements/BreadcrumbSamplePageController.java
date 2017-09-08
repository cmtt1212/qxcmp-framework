package com.qxcmp.framework.web.controller.sample.elements;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.breadcrumb.Breadcrumb;
import com.qxcmp.framework.web.view.elements.breadcrumb.BreadcrumbItem;
import com.qxcmp.framework.web.view.elements.breadcrumb.IconBreadCrumb;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/test/sample/breadcrumb")
public class BreadcrumbSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createTextBreadcrumbSegment()))
                        .addCol(new Col().addComponent(createIconBreadcrumbSegment()))
                )));
    }

    private Component createTextBreadcrumbSegment() {
        return new Segment().addComponent(new ContentHeader("文本分隔符面包屑", Size.LARGE).setDividing())
                .addComponent(new Segment().addComponent(new Breadcrumb().addItems(createItems())))
                .addComponent(new Segment().addComponent(new Breadcrumb("|").addItems(createItems())))
                ;
    }


    private Component createIconBreadcrumbSegment() {
        return new Segment().addComponent(new ContentHeader("图标分隔符面包屑", Size.LARGE).setDividing())
                .addComponent(new Segment().addComponent(new IconBreadCrumb().addItems(createItems())))
                .addComponent(new Segment().addComponent(new IconBreadCrumb("user").addItems(createItems())))
                ;
    }

    private Collection<? extends BreadcrumbItem> createItems() {
        List<BreadcrumbItem> items = Lists.newArrayList();
        int count = new Random().nextInt(4) + 3;
        for (int i = 0; i < count; i++) {
            if (new Random().nextBoolean()) {
                items.add(new BreadcrumbItem("导航", "/test/sample/breadcrumb"));
            } else {
                items.add(new BreadcrumbItem("文本"));
            }
        }
        items.get(items.size() - 1).setActive();
        return items;
    }

}
