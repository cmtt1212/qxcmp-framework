package com.qxcmp.framework.web.view.elements.segment;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.container.Container;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/elements/segments")
public class AbstractSegmentsController extends QXCMPController {

    @GetMapping("/1")
    public ModelAndView testPage9() {
        return page(() -> new Container().addComponent(new Segments()));
    }

    @GetMapping("/2")
    public ModelAndView testPage10() {
        return page(() -> new Container().addComponent(new HorizontalSegments()));
    }

    @GetMapping("/3")
    public ModelAndView testPage11() {
        return page(() -> new Container().addComponent(new PiledSegments()));
    }

    @GetMapping("/4")
    public ModelAndView testPage12() {
        return page(() -> new Container().addComponent(new RaisedSegments()));
    }

    @GetMapping("/5")
    public ModelAndView testPage13() {
        return page(() -> new Container().addComponent(new StackedSegments()));
    }
}
