package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.DividedGrid;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.H2;
import com.qxcmp.framework.web.view.elements.html.H4;
import com.qxcmp.framework.web.view.elements.icon.*;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/icon")
public class IconSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> {
            DividedGrid grid = new DividedGrid();
            grid.setColumnCount(ColumnCount.TWO);
            grid.addItem(new Col().addComponent(createBasicIconSegment()));
            grid.addItem(new Col().addComponent(createStateIconSegment()));
            grid.addItem(new Col().addComponent(createSizeIconSegment()));
            grid.addItem(new Col().addComponent(createInvertedIconSegment()));
            grid.addItem(new Col().addComponent(createCircularIconSegment()));
            grid.addItem(new Col().addComponent(createBorderedIconSegment()));
            grid.addItem(new Col().addComponent(createGroupsIconSegment()));
            return new Container().addComponent(new Segment().addComponent(new H2("图标")).addComponent(grid));
        });
    }

    private Component createBasicIconSegment() {
        return new Segment().addComponent(new H4("基本图标"))
                .addComponent(new Icon("user").setColor(randomColor()))
                .addComponent(new Icon("download").setColor(randomColor()))
                .addComponent(new Icon("close").setColor(randomColor()))
                .addComponent(new Icon("send").setColor(randomColor()))
                .addComponent(new Icon("theme").setColor(randomColor()))
                .addComponent(new Icon("undo").setColor(randomColor()))
                .addComponent(new Icon("edit").setColor(randomColor()))
                .addComponent(new Icon("wait").setColor(randomColor()))
                .addComponent(new Icon("user circle").setColor(randomColor()))
                ;
    }

    private Component createStateIconSegment() {
        return new Segment().addComponent(new H4("图标状态"))
                .addComponent(new Icon("user").setDisabled().setColor(randomColor()))
                .addComponent(new Icon("edit").setDisabled().setColor(randomColor()))
                .addComponent(new Icon("cloud").setDisabled().setColor(randomColor()))
                .addComponent(new Icon("spinner").setLoading().setColor(randomColor()))
                .addComponent(new Icon("notched circle").setLoading().setColor(randomColor()))
                .addComponent(new Icon("asterisk").setLoading().setColor(randomColor()));
    }

    private Component createSizeIconSegment() {
        return new Segment().addComponent(new H4("图标大小"))
                .addComponent(new Icon("html5").setColor(randomColor()).setSize(Size.MINI))
                .addComponent(new Icon("html5").setColor(randomColor()).setSize(Size.TINY))
                .addComponent(new Icon("html5").setColor(randomColor()).setSize(Size.SMALL))
                .addComponent(new Icon("html5").setColor(randomColor()))
                .addComponent(new Icon("html5").setColor(randomColor()))
                .addComponent(new Icon("html5").setColor(randomColor()).setSize(Size.LARGE))
                .addComponent(new Icon("html5").setColor(randomColor()).setSize(Size.HUGE))
                .addComponent(new Icon("html5").setColor(randomColor()).setSize(Size.MASSIVE));
    }

    private Component createInvertedIconSegment() {
        Segment segment = new Segment();
        segment.setInverted(true);
        return segment.addComponent(new H4("颜色翻转"))
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                .addComponent(new Icon("user").setColor(randomColor()).setInverted())
                ;
    }

    private Component createCircularIconSegment() {
        return new Segment().addComponent(new H4("圆形图标"))
                .addComponent(new CircularIcon("child").setColor(randomColor()))
                .addComponent(new CircularIcon("doctor").setColor(randomColor()))
                .addComponent(new CircularIcon("handicap").setColor(randomColor()))
                .addComponent(new CircularIcon("spy").setColor(randomColor()))
                .addComponent(new CircularIcon("student").setColor(randomColor()))
                .addComponent(new CircularIcon("user").setColor(randomColor()))
                .addComponent(new CircularIcon("user circle").setColor(randomColor()))
                .addComponent(new CircularIcon("user circle outline").setColor(randomColor()))
                .addComponent(new CircularIcon("user outline").setColor(randomColor()))
                .addComponent(new CircularIcon("users").setColor(randomColor()))
                ;
    }

    private Component createBorderedIconSegment() {
        return new Segment().addComponent(new H4("方形图标"))
                .addComponent(new BorderedIcon("announcement").setColor(randomColor()))
                .addComponent(new BorderedIcon("birthday").setColor(randomColor()))
                .addComponent(new BorderedIcon("help circle").setColor(randomColor()))
                .addComponent(new BorderedIcon("help").setColor(randomColor()))
                .addComponent(new BorderedIcon("info circle").setColor(randomColor()))
                .addComponent(new BorderedIcon("info").setColor(randomColor()))
                .addComponent(new BorderedIcon("warning circle").setColor(randomColor()))
                .addComponent(new BorderedIcon("warning").setColor(randomColor()))
                .addComponent(new BorderedIcon("warning sign").setColor(randomColor()))
                ;
    }

    private Component createGroupsIconSegment() {
        return new Segment().addComponent(new H4("图标叠加"))
                .addComponent(new Icons(new Icon("thin circle").setSize(Size.BIG), new Icon("user")).setSize(Size.HUGE))
                .addComponent(new Icons(new Icon("sun").setSize(Size.BIG).setLoading(), new Icon("user")).setSize(Size.HUGE))
                .addComponent(new CornerIcons(new Icon("user"), new CornerIcon("add")).setSize(Size.LARGE))
                ;
    }
}
