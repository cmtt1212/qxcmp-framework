package com.qxcmp.web.view.elements.divider;

import com.qxcmp.web.QxcmpController;
import com.qxcmp.web.view.elements.header.ContentHeader;
import com.qxcmp.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/divider")
public class AbstractDividerController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Divider()).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new HorizontalDivider("text")).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new HorizontalDivider(new ContentHeader("text", Size.SMALL))).build();
    }

}
