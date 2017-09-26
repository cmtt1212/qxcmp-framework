package com.qxcmp.framework.user.web;

import com.qxcmp.framework.web.QXCMPBackendController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/user")
@RequiredArgsConstructor
public class AdminUserPageController extends QXCMPBackendController {

    @GetMapping("")
    public ModelAndView userPage(Pageable pageable) {
        return page().addComponent(convertToTable(pageable, userService))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "用户管理")
                .build();
    }
}
