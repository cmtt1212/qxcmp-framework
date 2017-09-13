package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.loader.Loader;
import com.qxcmp.framework.web.view.elements.loader.SimpleLoader;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/loader")
public class LoaderSamplePageController extends AbstractSamplePageController {

    private final static String PLACEHOLDER = "/assets/images/placeholder-paragraph.png";

    @GetMapping("")
    public ModelAndView sample() {
        return page().addComponent( new Container().addComponent(new Grid().setColumnCount(ColumnCount.ONE)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createSegment1()))
                        .addCol(new Col().addComponent(createSegment2()))
                        .addCol(new Col().addComponent(createSegment3()))
                        .addCol(new Col().addComponent(createSegment4()))
                        .addCol(new Col().addComponent(createSegment5()))
                ))).build();
    }

    private Component createSegment1() {
        return new Segment().addComponent(new ContentHeader("标准加载器", Size.LARGE).setDividing())
                .addComponent(new Segment()
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Loader().setActive()))
                ;
    }

    private Component createSegment2() {
        return new Segment().addComponent(new ContentHeader("带文本加载器", Size.LARGE).setDividing())
                .addComponent(new Segment()
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Loader("加载中").setActive()))
                ;
    }

    private Component createSegment3() {
        return new Segment().addComponent(new ContentHeader("不确定状态加载器", Size.LARGE).setDividing())
                .addComponent(new Segment()
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Loader("加载中").setActive().setIndeterminate()))
                ;
    }

    private Component createSegment4() {
        return new Segment().addComponent(new ContentHeader("简单加载器", Size.LARGE).setDividing())
                .addComponent(new Segment()
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new Image(PLACEHOLDER))
                        .addComponent(new SimpleLoader("加载中").setActive()))
                ;
    }

    private Component createSegment5() {
        return new Segment().addComponent(new ContentHeader("加载器大小", Size.LARGE).setDividing())
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.MINI)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.TINY)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.SMALL)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.MEDIUM)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.LARGE)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.BIG)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.HUGE)))
                .addComponent(new Segment().addComponent(new Image(PLACEHOLDER)).addComponent(new Image(PLACEHOLDER)).addComponent(new Loader("加载中").setActive().setSize(Size.MASSIVE)))
                ;
    }

}
