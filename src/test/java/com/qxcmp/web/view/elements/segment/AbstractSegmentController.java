package com.qxcmp.web.view.elements.segment;

import com.qxcmp.web.QxcmpController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/segment")
public class AbstractSegmentController extends QxcmpController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page().addComponent(new Segment()).build();
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page().addComponent(new BasicSegment()).build();
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page().addComponent(new CircularSegment()).build();
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page().addComponent(new PiledSegment()).build();
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page().addComponent(new RaisedSegment()).build();
    }

    @GetMapping("/6")
    public ModelAndView testPage6() {
        return page().addComponent(new VerticalSegment()).build();
    }

    @GetMapping("/7")
    public ModelAndView testPage7() {
        return page().addComponent(new StackedSegment()).build();
    }

    @GetMapping("/8")
    public ModelAndView testPage8() {
        return page().addComponent(new TallStackedSegment()).build();
    }

}
