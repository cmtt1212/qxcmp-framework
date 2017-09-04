package com.qxcmp.framework.web.view.containers.grid;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.container.Container;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/containers/grid")
public class AbstractGridController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(() -> new Container().addComponent(new Grid()));
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(() -> new Container().addComponent(new DividedGrid()));
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(() -> new Container().addComponent(new VerticallyDividedGrid()));
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page(() -> new Container().addComponent(new CelledGrid()));
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page(() -> new Container().addComponent(new InternallyCelledGrid()));
    }
}
