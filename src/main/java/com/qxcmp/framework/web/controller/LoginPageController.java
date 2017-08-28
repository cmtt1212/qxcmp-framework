package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.view.ModelAndViewBuilder;
import com.qxcmp.framework.web.QXCMPController2;
import com.qxcmp.framework.web.view.Page;
import com.qxcmp.framework.web.view.elements.Container;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController2 {
    @Override
    protected ModelAndViewBuilder error(HttpStatus status, String message) {
        return null;
    }

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return builder("qxcmp")
                .addObject(Page.builder().component(Container.builder().component(() -> "qxcmp/components/login").build()).build())
                .build();
    }
}
