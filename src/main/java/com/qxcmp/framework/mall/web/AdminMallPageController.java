package com.qxcmp.framework.mall.web;

import com.qxcmp.framework.mall.StoreService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_MALL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_MALL_STORE;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/mall")
@RequiredArgsConstructor
public class AdminMallPageController extends QXCMPBackendController {

    private final StoreService storeService;

    @GetMapping("")
    public ModelAndView mallPage() {
        return page().addComponent(new Overview("商城管理"))
                .setBreadcrumb("控制台", "", "商城管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_MALL, "")
                .build();
    }

    @GetMapping("/store")
    public ModelAndView mallStorePage(Pageable pageable) {
        return page().addComponent(convertToTable("store", pageable, storeService))
                .setBreadcrumb("控制台", "", "商城管理", "mall/store", "店铺管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_MALL, NAVIGATION_ADMIN_MALL_STORE)
                .build();
    }
}
