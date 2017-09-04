package com.qxcmp.framework.web.view.elements.segment;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/segment")
public class AbstractSegmentController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(Segment::new);
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(BasicSegment::new);
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(CircularSegment::new);
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page(PiledSegment::new);
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page(RaisedSegment::new);
    }

    @GetMapping("/6")
    public ModelAndView testPage6() {
        return page(VerticalSegment::new);
    }

    @GetMapping("/7")
    public ModelAndView testPage7() {
        return page(StackedSegment::new);
    }

    @GetMapping("/8")
    public ModelAndView testPage8() {
        return page(TallStackedSegment::new);
    }

}
