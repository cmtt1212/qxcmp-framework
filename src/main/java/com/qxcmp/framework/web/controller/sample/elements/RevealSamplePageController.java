package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.Grid;
import com.qxcmp.framework.web.view.containers.grid.Row;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.Divider;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.reveal.*;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/reveal")
public class RevealSamplePageController extends AbstractSamplePageController {

    private final static String VISIBLE_IMAGE = "/assets/images/placeholder-square.png";
    private final static String HIDDEN_IMAGE = "/assets/images/profile-bg.jpg";

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new Grid().setColumnCount(ColumnCount.TWO)
                .addItem(new Row()
                        .addCol(new Col().addComponent(createSegment()))
                )));
    }

    private Component createSegment() {
        return new Segment().addComponent(new ContentHeader("展示元素", Size.LARGE).setDividing())
                .addComponent(new FadeReveal(new Image(VISIBLE_IMAGE).setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setSize(Size.SMALL)))
                .addComponent(new Divider())
                .addComponent(new FadeReveal(new Image(VISIBLE_IMAGE).setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setSize(Size.SMALL)).setInstant())
                .addComponent(new Divider())
                .addComponent(new MoveReveal(new Image(VISIBLE_IMAGE).setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setSize(Size.SMALL)))
                .addComponent(new Divider())
                .addComponent(new MoveRightReveal(new Image(VISIBLE_IMAGE).setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setSize(Size.SMALL)))
                .addComponent(new Divider())
                .addComponent(new MoveUpReveal(new Image(VISIBLE_IMAGE).setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setSize(Size.SMALL)))
                .addComponent(new Divider())
                .addComponent(new MoveDownReveal(new Image(VISIBLE_IMAGE).setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setSize(Size.SMALL)))
                .addComponent(new Divider())
                .addComponent(new RotateReveal(new Image(VISIBLE_IMAGE).setCircular().setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setCircular().setSize(Size.SMALL)))
                .addComponent(new Divider())
                .addComponent(new RotateLeftReveal(new Image(VISIBLE_IMAGE).setCircular().setSize(Size.SMALL), new Image(HIDDEN_IMAGE).setCircular().setSize(Size.SMALL)))
                ;
    }

}
