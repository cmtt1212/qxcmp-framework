package com.qxcmp.framework.web.view.elements.container;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.containers.grid.DividedGrid;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.VerticallyDividedGrid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/container")
public class AbstractContainerController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(() -> new Container().addComponent(new Grid()));
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(() -> new TextContainer().addComponent(new DividedGrid()));
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(() -> new FluidContainer().addComponent(new VerticallyDividedGrid()));
    }
}
