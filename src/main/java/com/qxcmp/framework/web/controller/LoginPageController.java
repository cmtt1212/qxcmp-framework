package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.Container;
import com.qxcmp.framework.web.view.elements.Image;
import com.qxcmp.framework.web.view.elements.Segment;
import com.qxcmp.framework.web.view.elements.header.Header;
import com.qxcmp.framework.web.view.support.LinkTarget;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page()
                .component(Container.builder().component(Segment.builder()
                        .component(Header.builder().title(qxcmpConfiguration.getTitle()).image(Image.builder().url("/").urlTarget(LinkTarget.BLANK.getValue()).source(qxcmpConfiguration.getLogo()).build()).build())
                        .component(Segment.builder().component(Image.builder().source(qxcmpConfiguration.getLogo()).centered(true).rounded(true).build()).build())
                        .component(() -> "qxcmp/components/login").build())
                        .build())
                .build()
                .build();
    }
}
