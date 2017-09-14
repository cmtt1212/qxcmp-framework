package com.qxcmp.framework.web.view.elements.header;

import com.qxcmp.framework.web.QXCMPFrontendController;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/header")
public class AbstractHeaderController extends QXCMPFrontendController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new PageHeader(HeaderType.H1, "")).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new ContentHeader("", Size.SMALL)).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new IconHeader("", new Icon("user"))).build();
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page().addComponent(new SubHeader("")).build();
    }
}
