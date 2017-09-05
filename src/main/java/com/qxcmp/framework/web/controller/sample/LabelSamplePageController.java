package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.label.*;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Pointing;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/label")
public class LabelSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createLabelSegment()))
                        .addCol(new Col().addComponent(createImageLabelSegment()))
                        .addCol(new Col().addComponent(createPointingLabelSegment()))
                        .addCol(new Col().addComponent(createCircularSegment()))
                        .addCol(new Col().addComponent(createLabelsSegment()))
                )));
    }

    private Component createLabelSegment() {
        return new Segment().addComponent(new ContentHeader("标准标签", Size.LARGE))
                .addComponent(new Label("标签文本").setColor(randomColor()).setUrl("#"))
                .addComponent(new Label("标签文本").setDetails("标签详情").setColor(randomColor()).setUrl("#"))
                .addComponent(new Label("标签文本").setIcon(new Icon("user")).setColor(randomColor()).setUrl("#"))
                .addComponent(new Label("标签文本").setImage(qxcmpConfiguration.getLogo()).setColor(randomColor()).setUrl("#"))
                ;
    }

    private Component createImageLabelSegment() {
        return new Segment().addComponent(new ContentHeader("图片标签", Size.LARGE))
                .addComponent(new ImageLabel("标签文本", qxcmpConfiguration.getLogo()).setColor(randomColor()).setUrl("#"))
                .addComponent(new ImageLabel("标签文本", qxcmpConfiguration.getLogo()).setDetails("标签详情").setColor(randomColor()).setUrl("#"))
                ;
    }

    private Component createPointingLabelSegment() {
        return new Segment().addComponent(new ContentHeader("指针标签", Size.LARGE))
                .addComponent(new PointingLabel("标签文本").setColor(randomColor()).setUrl("#"))
                .addComponent(new PointingLabel("标签文本", Pointing.BOTTOM_POINTING).setColor(randomColor()).setUrl("#"))
                .addComponent(new PointingLabel("标签文本", Pointing.LEFT_POINTING).setColor(randomColor()).setUrl("#"))
                .addComponent(new PointingLabel("标签文本", Pointing.RIGHT_POINTING).setColor(randomColor()).setUrl("#"))
                ;
    }

    private Component createCircularSegment() {
        return new Segment().addComponent(new ContentHeader("圆形标签", Size.LARGE))
                .addComponent(new CircularLabel("1").setColor(randomColor()).setUrl("#"))
                .addComponent(new CircularLabel("1").setColor(randomColor()).setUrl("#"))
                .addComponent(new CircularLabel("1").setColor(randomColor()).setUrl("#"))
                .addComponent(new CircularLabel("1").setColor(randomColor()).setUrl("#"))
                .addComponent(new EmptyCircularLabel().setColor(randomColor()).setUrl("#"))
                .addComponent(new EmptyCircularLabel().setColor(randomColor()).setUrl("#"))
                .addComponent(new EmptyCircularLabel().setColor(randomColor()).setUrl("#"))
                .addComponent(new EmptyCircularLabel().setColor(randomColor()).setUrl("#"))
                ;
    }

    private Component createLabelsSegment() {
        return new Segment().addComponent(new ContentHeader("标准标签组", Size.LARGE))
                .addComponent(new Labels().setColor(randomColor())
                        .addLabel(new Label("标签文本").setIcon(new Icon("user")))
                        .addLabel(new Label("标签文本").setIcon(new Icon("user")))
                        .addLabel(new Label("标签文本").setIcon(new Icon("user")))
                        .addLabel(new Label("标签文本").setIcon(new Icon("user")))
                );
    }
}
