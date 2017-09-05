package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.button.IconButton;
import com.qxcmp.framework.web.view.elements.button.IconButtons;
import com.qxcmp.framework.web.view.elements.button.LabeledButton;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.label.BasicLabel;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/button")
public class ButtonSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createStandardButtonSegment()))
                        .addCol(new Col().addComponent(createIconButtonSegment()))
                        .addCol(new Col().addComponent(createLabeledButtonSegment()))
                        .addCol(new Col().addComponent(createIconButtonsSegment()))
                )));
    }

    private Component createIconButtonsSegment() {
        return new Segment().addComponent(new ContentHeader("图标按钮组", Size.LARGE).setDividing())
                .addComponent(new IconButtons().setColor(randomColor())
                        .addButton(new IconButton("play"))
                        .addButton(new IconButton("pause"))
                        .addButton(new IconButton("shuffle"))
                );
    }

    private Component createStandardButtonSegment() {
        return new Segment().addComponent(new ContentHeader("标准按钮", Size.LARGE).setDividing())
                .addComponent(new Button("开始").setColor(randomColor()))
                .addComponent(new Button("开始").setActive().setColor(randomColor()))
                .addComponent(new Button("开始").setDisabled().setColor(randomColor()))
                .addComponent(new Button("开始").setLoading().setColor(randomColor()))
                .addComponent(new Button("开始").setCircular().setColor(randomColor()))
                ;
    }

    private Component createIconButtonSegment() {
        return new Segment().addComponent(new ContentHeader("图标按钮", Size.LARGE).setDividing())
                .addComponent(new IconButton("cloud").setColor(randomColor()))
                .addComponent(new IconButton("cloud").setActive().setColor(randomColor()))
                .addComponent(new IconButton("cloud").setDisabled().setColor(randomColor()))
                .addComponent(new IconButton("cloud").setLoading().setColor(randomColor()))
                .addComponent(new IconButton(new Icon("spinner").setLoading()).setColor(randomColor()))
                ;
    }

    private Component createLabeledButtonSegment() {
        return new Segment().addComponent(new ContentHeader("标签按钮", Size.LARGE).setDividing())
                .addComponent(new LabeledButton("喜欢", "2,048").setColor(randomColor()))
                .addComponent(new LabeledButton("喜欢", "2,048").setLeftLabel().setColor(randomColor()))
                .addComponent(new LabeledButton(new IconButton("fork"), new BasicLabel("2,048")).setLeftLabel().setColor(randomColor()))
                ;
    }
}
