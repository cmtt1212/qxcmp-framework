package com.qxcmp.framework.web.view.containers.grid;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/containers/grid")
public class AbstractGridController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(Grid::new);
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(DividedGrid::new);
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(VerticallyDividedGrid::new);
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page(CelledGrid::new);
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page(InternallyCelledGrid::new);
    }
}
