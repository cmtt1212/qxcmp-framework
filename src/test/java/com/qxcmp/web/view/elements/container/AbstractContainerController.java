package com.qxcmp.web.view.elements.container;

import com.qxcmp.web.QxcmpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/container")
public class AbstractContainerController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Container()).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new TextContainer()).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new FluidContainer()).build();
    }
}
