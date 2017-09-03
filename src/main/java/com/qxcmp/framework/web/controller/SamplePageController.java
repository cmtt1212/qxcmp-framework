package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.containers.grid.CelledGrid;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.segment.*;
import com.qxcmp.framework.web.view.support.Wide;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class SamplePageController extends QXCMPController {

    @GetMapping("/sample")
    public ModelAndView home() {
        return page(() -> new Container().addComponent(new CelledGrid().addItem(getSegmentSampleRow())));
    }

    private Row getSegmentSampleRow() {
        return new Row()
                .addCol(new Col(Wide.EIGHT).addComponent(new RaisedSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new PiledSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new StackedSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new TallStackedSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new Segment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new CircularSegment().addComponent(new Image(qxcmpConfiguration.getLogo(), "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new VerticalSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                .addCol(new Col(Wide.EIGHT).addComponent(new BasicSegment().addComponent(new Image("/assets/images/placeholder-paragraph.png", "/sample"))))
                ;
    }
}
