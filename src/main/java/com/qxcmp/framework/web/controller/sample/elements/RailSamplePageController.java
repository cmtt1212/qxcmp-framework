package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.container.TextContainer;
import com.qxcmp.framework.web.view.elements.divider.Divider;
import com.qxcmp.framework.web.view.elements.header.ContentHeader;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.image.LazyImage;
import com.qxcmp.framework.web.view.elements.rail.AbstractRail;
import com.qxcmp.framework.web.view.elements.rail.LeftRail;
import com.qxcmp.framework.web.view.elements.rail.RightRail;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/rail")
public class RailSamplePageController extends AbstractSamplePageController {

    private final static String PLACEHOLDER = "/assets/images/placeholder-paragraph.png";

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new TextContainer().addComponent(new VerticallyDividedGrid().setColumnCount(ColumnCount.ONE).setVerticallyPadded()
                .addItem(new Row()
                        .addCol(new Col().addComponent(createSegment("外部扶手").addComponent(createLeftRail()).addComponent(createRightRail())))
                        .addCol(new Col().addComponent(createSegment("内部扶手").addComponent(createLeftRail().setInternal()).addComponent(createRightRail().setInternal())))
                        .addCol(new Col().addComponent(createSegment("带分隔符的扶手").addComponent(createLeftRail().setDividing()).addComponent(createRightRail().setDividing())))
                        .addCol(new Col().addComponent(createSegment("无间距外部扶手").addComponent(createLeftRail().setAttached()).addComponent(createRightRail().setAttached())))
                        .addCol(new Col().addComponent(createSegment("无间距内部扶手").addComponent(createLeftRail().setAttached().setInternal()).addComponent(createRightRail().setAttached().setInternal())))
                        .addCol(new Col().addComponent(createSegment("窄距离扶手").addComponent(createLeftRail().setClose()).addComponent(createRightRail().setClose())))
                        .addCol(new Col().addComponent(createSegment("窄距离扶手").addComponent(createLeftRail().setVeryClose()).addComponent(createRightRail().setVeryClose())))

                )));
    }

    private Segment createSegment(String title) {
        return (Segment) new Segment().addComponent(new ContentHeader(title, Size.LARGE).setAlignment(Alignment.CENTER).setDividing())
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new Divider())
                .addComponent(new Image(PLACEHOLDER))
                .addComponent(new Divider())
                .addComponent(new Image(PLACEHOLDER))
                ;
    }

    private AbstractRail createLeftRail() {
        return new LeftRail(new Segment().addComponent(new ContentHeader("左扶手", Size.MEDIUM).setAlignment(Alignment.CENTER)).addComponent(new LazyImage(PLACEHOLDER)));
    }

    private AbstractRail createRightRail() {
        return new RightRail(new Segment().addComponent(new ContentHeader("右扶手", Size.MEDIUM).setAlignment(Alignment.CENTER)).addComponent(new LazyImage(PLACEHOLDER)));
    }

}
