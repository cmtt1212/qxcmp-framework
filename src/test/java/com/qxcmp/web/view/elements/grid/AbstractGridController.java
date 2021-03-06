package com.qxcmp.web.view.elements.grid;

import com.qxcmp.web.QxcmpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/containers/grid")
public class AbstractGridController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Grid()).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new DividedGrid()).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new VerticallyDividedGrid()).build();
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page().addComponent(new CelledGrid()).build();
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page().addComponent(new InternallyCelledGrid()).build();
    }
}
