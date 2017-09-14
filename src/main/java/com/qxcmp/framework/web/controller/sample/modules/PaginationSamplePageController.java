package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.pagination.Pagination;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/pagination")
public class PaginationSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample(Pageable pageable) {
        return page().addComponent(new Container().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Row()
                        .addCol(new Col().addComponent(new Segment().addComponent(new Pagination("/test/sample/pagination", "&search=content", pageable.getPageNumber() + 1, 500, pageable.getPageSize()))))
                        .addCol(new Col().addComponent(new Segment().addComponent(new Pagination("/test/sample/pagination", "&search=content", pageable.getPageNumber() + 1, 500, pageable.getPageSize()).setShowQuickJumper())))
                        .addCol(new Col().addComponent(new Segment().addComponent(new Pagination("/test/sample/pagination", "&search=content", pageable.getPageNumber() + 1, 500, pageable.getPageSize()).setShowQuickJumper().setShowSizeChanger())))
                        .addCol(new Col().addComponent(new Segment().addComponent(new Pagination("/test/sample/pagination", "&search=content", pageable.getPageNumber() + 1, 500, pageable.getPageSize()).setShowQuickJumper().setShowSizeChanger().setShowTotal())))
                        .addCol(new Col().addComponent(new Segment().addComponent(new Pagination("/test/sample/pagination", "&search=content", pageable.getPageNumber() + 1, 500, pageable.getPageSize()).setShowQuickJumper().setShowSizeChanger().setShowTotal().setShowTotalText("总共${total}个会员"))))
                ))).build();
    }
}
