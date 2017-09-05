package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.Divider;
import com.qxcmp.framework.web.view.elements.divider.HorizontalDivider;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/divider")
public class DividerSamplePageController extends AbstractSamplePageController {

    private final static String PLACEHOLDER = "/assets/images/placeholder-paragraph.png";

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createStandardDividerSegment()))
                        .addCol(new Col().addComponent(createHorizontalDividerSegment()))
                )));
    }

    private Component createHorizontalDividerSegment() {
        return new Segment().addComponent(new ContentHeader("水平分隔符", Size.LARGE).setDividing())
                .addComponent(new HorizontalDivider("分隔符"))
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new HorizontalDivider(new ContentHeader("分隔符", Size.SMALL)))
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new HorizontalDivider(new ContentHeader("分隔符", Size.SMALL).setSubTitle("子标题").setIcon(new Icon("edit"))))
                .addComponent(new Image(PLACEHOLDER))
                ;
    }

    private Component createStandardDividerSegment() {
        return new Segment().addComponent(new ContentHeader("标准分隔符", Size.LARGE).setDividing())
                .addComponent(new Divider())
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new Divider().setInverted())
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new Divider().setSection())
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new Divider().setFitted())
                .addComponent(new Image(PLACEHOLDER))
                ;
    }

}
