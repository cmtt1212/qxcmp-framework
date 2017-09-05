package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.*;
import com.qxcmp.framework.web.view.elements.segment.AbstractSegment;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.AnchorTarget;
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
                        .addCol(new Col(Wide.EIGHT).addComponent(createInlineElements())))));
    }

    private AbstractSegment createInlineElements() {
        return new Segment()
                .addComponent(new H2("内联基本元素"))
                .addComponent(new Anchor("超链接", "/test/sample/html"))
                .addComponent(new Anchor("超链接 - 本窗口打开", "/test/sample/html", AnchorTarget.BLANK.toString()))
                .addComponent(new Span("span"));
    }

    private AbstractSegment createBlockElements() {
        return new Segment()
                .addComponent(new H2("块级基本元素"))
                .addComponent(new H1("h1"))
                .addComponent(new H2("h2"))
                .addComponent(new H3("h3"))
                .addComponent(new H4("h4"))
                .addComponent(new H5("h5"))
                .addComponent(new H6("h6"))
                .addComponent(new P("p"))
                .addComponent(new Div("div"));
    }
}
