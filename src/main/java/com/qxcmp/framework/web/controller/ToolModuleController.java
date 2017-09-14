package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.view.component.ElementType;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 系统工具默认页面路由
 *
 * @author aaric
 */
@RequestMapping(QXCMP_BACKEND_URL + "/tool")
@Controller
public class ToolModuleController extends QXCMPBackendController2 {

    @GetMapping
    public ModelAndView tool() {
        return builder().setTitle("系统工具")
                .addElement(ElementType.H4, "系统工具")
                .addNavigation(Navigation.Type.NORMAL, "系统工具")
                .build();
    }

    @GetMapping("/account/invite")
    public ModelAndView accountInvite() {
        return builder().setTitle("")
                .addNavigation("账户邀请码", Navigation.Type.NORMAL, "系统工具")
                .build();
    }
}
