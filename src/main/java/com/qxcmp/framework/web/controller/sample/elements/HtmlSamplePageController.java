package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.html.*;
import com.qxcmp.framework.web.view.elements.segment.AbstractSegment;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.Size;
import com.qxcmp.framework.web.view.support.Wide;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/html")
public class HtmlSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid()
                .addItem(new Row()
                        .addCol(new Col(Wide.EIGHT).addComponent(createBlockElements()))
                        .addCol(new Col(Wide.EIGHT).addComponent(createInlineElements()))
                )));
    }

    private AbstractSegment createInlineElements() {
        return new Segment().addComponent(new ContentHeader("内联基本元素", Size.LARGE).setDividing())
                .addComponent(new Anchor("超链接", "/test/sample/html"))
                .addComponent(new Anchor("超链接 - 本窗口打开", "/test/sample/html", AnchorTarget.BLANK.toString()))
                .addComponent(new Span("span"))
                ;
    }

    private AbstractSegment createBlockElements() {
        return new Segment().addComponent(new ContentHeader("块级基本元素", Size.LARGE).setDividing())
                .addComponent(new H1("标题一"))
                .addComponent(new H2("标题二"))
                .addComponent(new H3("标题三"))
                .addComponent(new H4("标题四"))
                .addComponent(new H5("标题五"))
                .addComponent(new H6("标题六"))
                .addComponent(new P().addInlineElement(new Span("段落")).addInlineElement(new Anchor("嵌套超链接", "/test/sample/html")))
                .addComponent(new Div("div"))
                ;
    }
}
