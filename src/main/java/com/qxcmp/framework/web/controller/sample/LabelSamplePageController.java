package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.label.ImageLabel;
import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
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

}
