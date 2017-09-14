package com.qxcmp.framework.web.view.elements.segment;

import com.qxcmp.framework.web.QXCMPFrontendController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/segments")
public class AbstractSegmentsController extends QXCMPFrontendController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Segments()).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new HorizontalSegments()).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new PiledSegments()).build();
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page().addComponent(new RaisedSegments()).build();
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page().addComponent(new StackedSegments()).build();
    }
}
