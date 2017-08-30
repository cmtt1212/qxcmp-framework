package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.containers.Container;
import com.qxcmp.framework.web.view.containers.Segment;
import com.qxcmp.framework.web.view.containers.Segments;
import com.qxcmp.framework.web.view.elements.Header;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.LinkTarget;
import com.qxcmp.framework.web.view.support.Size;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page(() -> {
            Container container = nextComponent(Container.class);
            Segments segments = nextComponent(Segments.class);
            Segment headerSegment = nextComponent(Segment.class);
            Header header = nextComponent(Header.class);
            Image image = nextComponent(Image.class);
            image.setSource(qxcmpConfiguration.getLogo());
            image.setUrl("/");
            image.setUrlTarget(LinkTarget.BLANK.getValue());
            header.setIconHeader(true);
            header.setTitle(qxcmpConfiguration.getTitle());
            header.setAlignment(Alignment.CENTER);
            header.setImage(image);
            headerSegment.getComponents().add(header);
            Segment formSegment = nextComponent(Segment.class);
            formSegment.getComponents().add(new QXCMPComponent("qxcmp/components/login") {
            });
            segments.getSegments().add(headerSegment);
            segments.getSegments().add(formSegment);

            Segment buttonSegment = nextComponent(Segment.class);
            Button button1 = nextComponent(Button.class);
            button1.setText("测试按钮");
            button1.setPrimary(true);
            button1.setSize(Size.LARGE);
            buttonSegment.getComponents().add(button1);
            segments.getSegments().add(buttonSegment);
            container.getComponents().add(segments);
            return container;
        });
    }
}
