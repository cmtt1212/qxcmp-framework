package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.Menu;
import com.qxcmp.framework.web.view.elements.MenuItem;
import com.qxcmp.framework.web.view.elements.Image;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL)
public class AdminPageController extends QXCMPController {

    @GetMapping("")
    public ModelAndView home() {
        return page(pageBuilder -> pageBuilder.component(Menu.builder()
                .item(MenuItem.builder().image(Image.builder().source(qxcmpConfiguration.getLogo()).build()).build())
                .item(MenuItem.builder().text("首页").url("/").build())
                .build())).build();
    }
}
