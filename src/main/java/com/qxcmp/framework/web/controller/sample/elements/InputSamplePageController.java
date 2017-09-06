package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.input.IconInput;
import com.qxcmp.framework.web.view.elements.input.Input;
import com.qxcmp.framework.web.view.elements.input.LabeledInput;
import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/input")
public class InputSamplePageController extends AbstractSamplePageController {

    public static final String PLACEHOLDER = "请输入搜索内容...";

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createStandardInputSegment()))
                        .addCol(new Col().addComponent(createIconInputSegment()))
                        .addCol(new Col().addComponent(createLabelInputSegment()))
                )));
    }

    private Component createLabelInputSegment() {
        return new Segment().addComponent(new ContentHeader("标签输入框", Size.LARGE).setDividing())
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new Label("http://")))
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new Label("kg")).setRightLabel())
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new Label("0.00"), new Label("￥")))
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new Label("0.00"), new Label("￥")).setRightLabel())
                ;
    }

    private Component createIconInputSegment() {
        return new Segment().addComponent(new ContentHeader("标准输入框", Size.LARGE).setDividing())
                .addComponent(new IconInput("search", PLACEHOLDER))
                .addComponent(new IconInput("users", PLACEHOLDER).setLeftIcon())
                .addComponent(new IconInput(new Icon("circular search").setLink(), PLACEHOLDER))
                .addComponent(new IconInput(new Icon("circular search").setLink().setInverted(), PLACEHOLDER))
                ;
    }

    private Component createStandardInputSegment() {
        return new Segment().addComponent(new ContentHeader("图标输入框", Size.LARGE).setDividing())
                .addComponent(new Input(PLACEHOLDER))
                .addComponent(new Input(PLACEHOLDER).setFocus())
                .addComponent(new Input(PLACEHOLDER).setDisabled())
                .addComponent(new Input(PLACEHOLDER).setError())
                .addComponent(new Input(PLACEHOLDER).setFluid())
                ;
    }
}
