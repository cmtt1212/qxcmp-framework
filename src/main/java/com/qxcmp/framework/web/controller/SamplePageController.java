package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.image.Avatar;
import com.qxcmp.framework.web.view.elements.image.CircularImage;
import com.qxcmp.framework.web.view.elements.image.Image;
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
            container.getComponents().add(new RaisedSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample")));
            container.getComponents().add(new PiledSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample")));
            container.getComponents().add(new StackedSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample")));
            container.getComponents().add(new TallStackedSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample")));
            container.getComponents().add(new Segment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample")));
            container.getComponents().add(new CircularSegment().addComponent(new CircularImage(qxcmpConfiguration.getLogo(), "/sample")));
            container.getComponents().add(new VerticalSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample")));
            container.getComponents().add(new BasicSegment().addComponent(new Avatar(qxcmpConfiguration.getLogo(), "/sample")));
            return container;
        });
    }
}
