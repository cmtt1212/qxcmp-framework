package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.dropdown.Dropdown;
import com.qxcmp.framework.web.view.modules.dropdown.DropdownMenu;
import com.qxcmp.framework.web.view.modules.dropdown.Selection;
import com.qxcmp.framework.web.view.modules.dropdown.SelectionMenu;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
                .addComponent(new Selection("国家").setMenu(new SelectionMenu()))
                ;
    }

    private Component createDropdownSegment() {
        return new Segment().addComponent(new ContentHeader("下拉框", Size.LARGE).setDividing())
                .addComponent(new Dropdown("性别").setMenu(new DropdownMenu()))
                ;
    }
}
