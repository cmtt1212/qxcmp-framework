package com.qxcmp.framework.web.view.elements.icon;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/icon")
public class IconController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(() -> new Icon("user"));
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(() -> new BorderedIcon("user"));
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(() -> new CircularIcon("user"));
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page(() -> new CornerIcon("user"));
    }

}
