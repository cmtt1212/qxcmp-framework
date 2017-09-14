package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL)
public class AdminPageController extends QXCMPBackendController {

    @GetMapping("")
    public ModelAndView adminHomePage() {
        return page()
                .addComponent(new PageHeader(HeaderType.H1, qxcmpConfiguration.getTitle()))
                .build();
    }
}
