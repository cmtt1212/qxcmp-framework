package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.*;
import com.qxcmp.framework.web.view.support.LinkTarget;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page()
                .component(Container.builder().component(Segments.builder().horizontal(true)
                        .segment(Segment.builder().component(Header.builder().iconHeader(true).title(qxcmpConfiguration.getTitle()).image(Image.builder().url("/").urlTarget(LinkTarget.BLANK.getValue()).source(qxcmpConfiguration.getLogo()).build()).build()).build())
                        .segment(Segment.builder().component(() -> "qxcmp/components/login").build())
                        .build())
                        .build())
                .build()
                .build();
    }
}
