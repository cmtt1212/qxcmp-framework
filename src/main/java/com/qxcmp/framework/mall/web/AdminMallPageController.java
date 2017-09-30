package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_MALL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/mall")
@RequiredArgsConstructor
public class AdminMallPageController extends QXCMPBackendController {

    @GetMapping("")
    public ModelAndView newsPage() {
        return page().addComponent(new Overview("商城管理"))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "商城管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_MALL, "")
                .build();
    }
}
