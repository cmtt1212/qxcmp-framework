package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.Container;
import com.qxcmp.framework.web.view.elements.Icon;
import com.qxcmp.framework.web.view.elements.Segment;
import com.qxcmp.framework.web.view.elements.header.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page()
                .component(Container.builder().component(Segment.builder()
                        .component(Header.builder().title(qxcmpConfiguration.getTitle()).subTitle("测试页眉子标题").icon(new Icon("user")).iconHeader(true).build())
                        .component(() -> "qxcmp/components/login").build())
                        .build())
                .build()
                .build();
    }
}
