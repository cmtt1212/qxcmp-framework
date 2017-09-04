package com.qxcmp.framework.web.view.elements.segment;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.container.Container;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/segment")
public class AbstractSegmentController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage1() {
        return page(() -> new Container().addComponent(new Segment()));
    }

    @GetMapping("/2")
    public ModelAndView testPage2() {
        return page(() -> new Container().addComponent(new BasicSegment()));
    }

    @GetMapping("/3")
    public ModelAndView testPage3() {
        return page(() -> new Container().addComponent(new CircularSegment()));
    }

    @GetMapping("/4")
    public ModelAndView testPage4() {
        return page(() -> new Container().addComponent(new PiledSegment()));
    }

    @GetMapping("/5")
    public ModelAndView testPage5() {
        return page(() -> new Container().addComponent(new RaisedSegment()));
    }

    @GetMapping("/6")
    public ModelAndView testPage6() {
        return page(() -> new Container().addComponent(new VerticalSegment()));
    }

    @GetMapping("/7")
    public ModelAndView testPage7() {
        return page(() -> new Container().addComponent(new StackedSegment()));
    }

    @GetMapping("/8")
    public ModelAndView testPage8() {
        return page(() -> new Container().addComponent(new TallStackedSegment()));
    }

}
