package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.*;
import com.qxcmp.framework.web.view.elements.icon.CircularIcon;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.segment.AbstractSegment;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/header")
public class HeaderSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createPageHeaderSegment()))
                        .addCol(new Col().addComponent(createContentHeaderSegment()))
                        .addCol(new Col().addComponent(createIconHeaderSegment()))
                        .addCol(new Col().addComponent(createSubHeaderSegment()))
                )));
    }

    private Component createSubHeaderSegment() {
        return new Segment().addComponent(new ContentHeader("子抬头", Size.LARGE).setDividing())
                .addComponent(new SubHeader("抬头标题"))
                .addComponent(new SubHeader("抬头标题").setImage(new Image(qxcmpConfiguration.getLogo())).setColor(randomColor()))
                .addComponent(new SubHeader("抬头标题").setIcon(new Icon("cloud").setColor(randomColor())).setColor(randomColor()))
                .addComponent(new SubHeader("抬头标题").setSubTitle("子标题").setImage(new Image(qxcmpConfiguration.getLogo())).setColor(randomColor()))
                .addComponent(new SubHeader("抬头标题").setSubTitle("子标题").setIcon(new Icon("cloud").setColor(randomColor())).setColor(randomColor()))
                ;
    }

    private Component createIconHeaderSegment() {
        return new Segment().addComponent(new ContentHeader("图标抬头", Size.LARGE).setDividing())
                .addComponent(new IconHeader("图标抬头", new Icon("settings").setColor(randomColor())).setAlignment(Alignment.CENTER).setColor(randomColor()))
                .addComponent(new IconHeader("图标抬头", new Icon("setting").setColor(randomColor()).setLoading()).setAlignment(Alignment.CENTER).setColor(randomColor()))
                .addComponent(new IconHeader("图标抬头", new CircularIcon("users").setColor(randomColor())).setAlignment(Alignment.CENTER).setColor(randomColor()))
                ;
    }

    private Component createContentHeaderSegment() {
        return new Segment().addComponent(new ContentHeader("内容抬头", Size.LARGE).setDividing())
                .addComponent(new ContentHeader("抬头标题", Size.SMALL))
                .addComponent(new ContentHeader("抬头标题", Size.SMALL).setImage(new Image(qxcmpConfiguration.getLogo())).setColor(randomColor()))
                .addComponent(new ContentHeader("抬头标题", Size.SMALL).setIcon(new Icon("cloud").setColor(randomColor())).setColor(randomColor()))
                .addComponent(new ContentHeader("抬头标题", Size.SMALL).setSubTitle("子标题").setImage(new Image(qxcmpConfiguration.getLogo())).setColor(randomColor()))
                .addComponent(new ContentHeader("抬头标题", Size.SMALL).setSubTitle("子标题").setIcon(new Icon("cloud").setColor(randomColor())).setColor(randomColor()))
                ;
    }

    private AbstractSegment createPageHeaderSegment() {
        return new Segment().addComponent(new ContentHeader("页面抬头", Size.LARGE).setDividing())
                .addComponent(new PageHeader(HeaderType.H4, "抬头标题"))
                .addComponent(new PageHeader(HeaderType.H4, "抬头标题").setImage(new Image(qxcmpConfiguration.getLogo())).setColor(randomColor()))
                .addComponent(new PageHeader(HeaderType.H4, "抬头标题").setIcon(new Icon("cloud").setColor(randomColor())).setColor(randomColor()))
                .addComponent(new PageHeader(HeaderType.H4, "抬头标题").setSubTitle("子标题").setImage(new Image(qxcmpConfiguration.getLogo())).setColor(randomColor()))
                .addComponent(new PageHeader(HeaderType.H4, "抬头标题").setSubTitle("子标题").setIcon(new Icon("cloud").setColor(randomColor())).setColor(randomColor()))
                ;
    }
}
