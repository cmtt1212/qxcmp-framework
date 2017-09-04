package com.qxcmp.framework.web.view.elements.segment;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/segments")
public class AbstractSegmentsController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(Segments::new);
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(HorizontalSegments::new);
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(PiledSegments::new);
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page(RaisedSegments::new);
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page(StackedSegments::new);
    }
}
