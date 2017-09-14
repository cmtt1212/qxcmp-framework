package com.qxcmp.framework.web.view.elements.icon;

import com.qxcmp.framework.web.QXCMPFrontendController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/icon")
public class IconController extends QXCMPFrontendController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Icon("user")).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new BorderedIcon("user")).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new CircularIcon("user")).build();
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page().addComponent(new CornerIcon("user")).build();
    }

}
