package com.qxcmp.framework.web.controller.sample.modules;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.label.EmptyCircularLabel;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.dropdown.Dropdown;
import com.qxcmp.framework.web.view.modules.dropdown.DropdownMenu;
import com.qxcmp.framework.web.view.modules.dropdown.Selection;
import com.qxcmp.framework.web.view.modules.dropdown.SelectionMenu;
import com.qxcmp.framework.web.view.modules.dropdown.item.*;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/test/sample/dropdown")
public class DropdownSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createDropdownSegment()))
                        .addCol(new Col().addComponent(createSelectionSegment()))
                )));
    }

    private Component createSelectionSegment() {
        return new Segment().addComponent(new ContentHeader("选择框", Size.LARGE).setDividing())
                .addComponent(new Selection("国家").setMenu(createSelectionMenu()))
                ;
    }

    private SelectionMenu createSelectionMenu() {
        return new SelectionMenu()
                .addItems(createTextItems())
                .addItems(createIconItems())
                .addItems(createImageItems())
                .addItems(createLabelItems())
                ;
    }

    private Component createDropdownSegment() {
        return new Segment().addComponent(new ContentHeader("下拉框", Size.LARGE).setDividing())
                .addComponent(new Dropdown("密保问题").setMenu(createDropdownMenu()))
                ;
    }

    private DropdownMenu createDropdownMenu() {
        return new DropdownMenu()
                .addItem(new HeaderItem("第一组问题", "help"))
                .addItem(new DividerItem())
                .addItems(createTextItems())
                .addItem(new HeaderItem("第二组问题", "help"))
                .addItem(new DividerItem())
                .addItems(createIconItems())
                .addItem(new HeaderItem("第三组问题", "help"))
                .addItem(new DividerItem())
                .addItems(createImageItems())
                .addItem(new HeaderItem("第四组问题", "help"))
                .addItem(new DividerItem())
                .addItems(createLabelItems())
                ;
    }

    private List<TextItem> createTextItems() {
        List<TextItem> items = Lists.newArrayList();
        int count = new Random().nextInt(3) + 1;
        for (int i = 0; i < count; i++) {
            items.add(new TextItem(RandomStringUtils.randomAlphabetic(5, 10)));
        }
        return items;
    }

    private List<IconItem> createIconItems() {
        List<IconItem> items = Lists.newArrayList();
        int count = new Random().nextInt(3) + 1;
        for (int i = 0; i < count; i++) {
            items.add(new IconItem("选项文本", "user"));
        }
        return items;
    }

    private List<ImageItem> createImageItems() {
        List<ImageItem> items = Lists.newArrayList();
        int count = new Random().nextInt(3) + 1;
        for (int i = 0; i < count; i++) {
            items.add(new ImageItem("选项文本", qxcmpConfiguration.getLogo()));
        }
        return items;
    }

    private List<LabelItem> createLabelItems() {
        List<LabelItem> items = Lists.newArrayList();
        int count = new Random().nextInt(3) + 1;
        for (int i = 0; i < count; i++) {
            items.add(new LabelItem("选项文本", new EmptyCircularLabel().setColor(randomColor())));
        }
        return items;
    }
}
