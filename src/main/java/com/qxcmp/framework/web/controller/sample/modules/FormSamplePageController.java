package com.qxcmp.framework.web.controller.sample.modules;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.form.*;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/form")
public class FormSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new VerticallyDividedGrid().setContainer().setVerticallyPadded().setColumnCount(ColumnCount.ONE)
                .addItem(new Col().addComponent(createFormSegment()))
        );
    }

    private Component createFormSegment() {
        return new Segment().addComponent(new Form().setAction("/test/sample/form").setMethod(FormMethod.POST).setEnctype(FormEnctype.APPLICATION)
                .addSection(new FormSection("基本资料"))
                .addItem(new TextInputField("text-input", "用户名"))
                .addSection(new FormSection("补充资料"))
        );
    }

}
