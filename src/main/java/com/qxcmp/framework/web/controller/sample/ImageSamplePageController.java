package com.qxcmp.framework.web.controller.sample;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.containers.grid.Col;
import com.qxcmp.framework.web.view.containers.grid.DividedGrid;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.html.H4;
import com.qxcmp.framework.web.view.elements.image.Avatar;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.image.Images;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test/sample/image")
public class ImageSamplePageController extends AbstractSamplePageController {

    @GetMapping("")
    public ModelAndView sample() {
        return page(() -> new Container().addComponent(new DividedGrid().setColumnCount(ColumnCount.TWO)
                .addItem(new Col().addComponent(createAvatarImageSegment()))
                .addItem(new Col().addComponent(createCircularImageSegment()))
        ));
    }

    private Component createCircularImageSegment() {
        return new Segment().addComponent(new H4("一般图片"))
                .addComponent(new Images().setSize(Size.SMALL)
                        .addImage(new Image("/assets/images/placeholder.png"))
                        .addImage(new Image("/assets/images/placeholder.png"))
                        .addImage(new Image("/assets/images/placeholder.png"))
                        .addImage(new Image("/assets/images/placeholder.png"))
                        .addImage(new Image("/assets/images/placeholder.png"))
                );
    }

    private Component createAvatarImageSegment() {
        return new Segment().addComponent(new H4("头像"))
                .addComponent(new Avatar(qxcmpConfiguration.getLogo()))
                .addComponent(new Avatar(qxcmpConfiguration.getLogo()))
                .addComponent(new Avatar(qxcmpConfiguration.getLogo()))
                .addComponent(new Avatar(qxcmpConfiguration.getLogo()))
                .addComponent(new Avatar(qxcmpConfiguration.getLogo()))
                ;
    }

}
