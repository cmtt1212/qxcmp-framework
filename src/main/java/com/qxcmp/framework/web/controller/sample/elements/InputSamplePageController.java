package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.button.IconButton;
import com.qxcmp.framework.web.view.elements.button.LabeledIconButton;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.input.ActionInput;
import com.qxcmp.framework.web.view.elements.input.IconInput;
import com.qxcmp.framework.web.view.elements.input.Input;
import com.qxcmp.framework.web.view.elements.input.LabeledInput;
import com.qxcmp.framework.web.view.elements.label.BasicLabel;
import com.qxcmp.framework.web.view.elements.label.Label;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.dropdown.Dropdown;
import com.qxcmp.framework.web.view.modules.dropdown.DropdownMenu;
import com.qxcmp.framework.web.view.modules.dropdown.item.TextItem;
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
                        .addCol(new Col().addComponent(createActionInputSegment()))
                )));
    }

    private Component createActionInputSegment() {
        return new Segment().addComponent(new ContentHeader("行动输入框", Size.LARGE).setDividing())
                .addComponent(new ActionInput(new Input(PLACEHOLDER), new Button("开始搜索")))
                .addComponent(new ActionInput(new Input(PLACEHOLDER), new LabeledIconButton("我的购物车", new Icon("cart")).setColor(randomColor())).setLeftAction())
                .addComponent(new ActionInput(new IconInput("search", PLACEHOLDER).setLeftIcon(), new Dropdown("搜索范围").setMenu(new DropdownMenu().addItem(new TextItem("当前页面")).addItem(new TextItem("全站"))).setFloating()))
                .addComponent(new ActionInput(new Input(PLACEHOLDER).setValue("https://www.qxcmp.com"), new LabeledIconButton("复制", new Icon("copy")).setRightIcon().setColor(randomColor())))
                .addComponent(new ActionInput(new Input(PLACEHOLDER), new IconButton("search")))
                ;
    }

    private Component createLabelInputSegment() {
        return new Segment().addComponent(new ContentHeader("标签输入框", Size.LARGE).setDividing())
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new Label("http://")))
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new BasicLabel("kg")).setRightLabel())
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new BasicLabel("0.00"), new Label("￥")))
                .addComponent(new LabeledInput(new Input(PLACEHOLDER), new BasicLabel("0.00"), new Label("￥")).setRightLabel())
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
