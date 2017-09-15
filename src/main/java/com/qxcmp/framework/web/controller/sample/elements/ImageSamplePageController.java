package com.qxcmp.framework.web.controller.sample.elements;

import com.qxcmp.framework.web.controller.sample.AbstractSamplePageController;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.DividedGrid;
import com.qxcmp.framework.web.view.elements.html.H4;
import com.qxcmp.framework.web.view.elements.image.Avatar;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.image.Images;
import com.qxcmp.framework.web.view.elements.image.LazyImage;
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

    public static final String PLACEHOLDER = "/assets/images/placeholder.png";
    public static final String BACKGROUND = "/assets/images/profile-bg.jpg";

    @GetMapping("")
    public ModelAndView sample() {
        return page().addComponent(new Container().addComponent(new DividedGrid().setColumnCount(ColumnCount.TWO)
                .addItem(new Col().addComponent(createAvatarImageSegment()))
                .addItem(new Col().addComponent(createCircularImageSegment()))
                .addItem(new Col().addComponent(createFluidImageSegment()))
                .addItem(new Col().addComponent(createLazyLoadImageSegment()))
        )).build();
    }

    private Component createLazyLoadImageSegment() {
        return new Segment().addComponent(new H4("懒加载图片"))
                .addComponent(new LazyImage(BACKGROUND, "/test/sample/image").setSize(Size.HUGE))
                .addComponent(new LazyImage(BACKGROUND, "/test/sample/image").setSize(Size.HUGE))
                .addComponent(new LazyImage(BACKGROUND, "/test/sample/image").setSize(Size.HUGE))
                .addComponent(new LazyImage(BACKGROUND, "/test/sample/image").setSize(Size.HUGE))
                .addComponent(new LazyImage(BACKGROUND, "/test/sample/image").setSize(Size.HUGE))
                .addComponent(new LazyImage(BACKGROUND, "/test/sample/image").setDuration(5000).setSize(Size.HUGE))
                ;
    }

    private Component createFluidImageSegment() {
        return new Segment().addComponent(new H4("满宽图片"))
                .addComponent(new Image(PLACEHOLDER).setFluid())
                .addComponent(new Image(PLACEHOLDER).setFluid())
                .addComponent(new Image(PLACEHOLDER).setFluid())
                .addComponent(new Image(PLACEHOLDER).setFluid())
                ;
    }

    private Component createCircularImageSegment() {
        return new Segment().addComponent(new H4("一般图片"))
                .addComponent(new Images().setSize(Size.SMALL)
                        .addImage(new Image(PLACEHOLDER))
                        .addImage(new Image(PLACEHOLDER))
                        .addImage(new Image(PLACEHOLDER))
                        .addImage(new Image(PLACEHOLDER))
                        .addImage(new Image(PLACEHOLDER))
                );
    }

    private Component createAvatarImageSegment() {
        return new Segment().addComponent(new H4("头像"))
                .addComponent(new Avatar(siteService.getLogo()))
                .addComponent(new Avatar(siteService.getLogo()))
                .addComponent(new Avatar(siteService.getLogo()))
                .addComponent(new Avatar(siteService.getLogo()))
                .addComponent(new Avatar(siteService.getLogo()))
                ;
    }

}
