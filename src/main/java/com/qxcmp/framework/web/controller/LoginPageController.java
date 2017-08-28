package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.Container;
import com.qxcmp.framework.web.view.elements.Segment;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page()
                .component(Container.builder().component(Segment.builder()
                        .component(PageHeader.builder().text(qxcmpConfiguration.getTitle()).build())
                        .component(() -> "qxcmp/components/login").build())
                        .build())
                .build()
                .build();
    }
}
