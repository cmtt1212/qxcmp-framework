package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginPageController extends QXCMPController {

    @GetMapping("/login")
    public ModelAndView loginPage() {
        return page()
                .component(() -> "qxcmp/components/login").build()
                .build();
    }
}
