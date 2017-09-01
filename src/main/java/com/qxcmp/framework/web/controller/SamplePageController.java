package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.segment.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class SamplePageController extends QXCMPController {

    @GetMapping("/sample")
    public ModelAndView home() {
        return page(() -> {
            final Container container = new Container();
            container.getComponents().add(new RaisedSegment());
            container.getComponents().add(new PiledSegment());
            container.getComponents().add(new StackedSegment());
            container.getComponents().add(new TallStackedSegment());
            container.getComponents().add(new Segment());
            container.getComponents().add(new CircularSegment());
            container.getComponents().add(new VerticalSegment());
            container.getComponents().add(new BasicSegment());
            return container;
        });
    }
}
