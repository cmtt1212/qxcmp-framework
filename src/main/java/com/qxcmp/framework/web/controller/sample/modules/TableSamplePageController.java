package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.table.*;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/table")
public class TableSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page().addComponent( new Container().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Row()
                        .addCol(new Col().addComponent(new Segment().addComponent(createTable1())))
                ))).build();
    }

    private Component createTable1() {
        return new Table().setCelled().setSelectable().setColor(randomColor())
                .setHeader(createTableHeader())
                .setBody(createTableBody())
                .setFooter(createTableFooter())
                ;
    }

    private TableBody createTableBody() {
        return (TableBody) new TableBody()
                .addRow(new TableRow().addCell(new TableData("用户一")).addCell(new TableData("男")).addCell(new TableData("13088888888")))
                .addRow(new TableRow().addCell(new TableData("用户二")).addCell(new TableData("女")).addCell(new TableData("13588888888")))
                .addRow(new TableRow().addCell(new TableData("用户三")).addCell(new TableData("女")).addCell(new TableData("18588888888")))
                ;
    }

    private TableHeader createTableHeader() {
        return (TableHeader) new TableHeader()
                .addRow(new TableRow()
                        .addCell(new TableHead("用户名"))
                        .addCell(new TableHead("性别"))
                        .addCell(new TableHead("联系电话"))
                );
    }

    private TableFooter createTableFooter() {
        return (TableFooter) new TableFooter()
                .addRow(new TableRow()
                        .addCell(new TableHead("总计： 3人").setColSpan(3))
                );
    }

}
