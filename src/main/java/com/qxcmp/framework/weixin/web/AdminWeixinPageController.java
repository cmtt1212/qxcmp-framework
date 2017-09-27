package com.qxcmp.framework.weixin.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/weixin")
@RequiredArgsConstructor
public class AdminWeixinPageController extends QXCMPBackendController {

    @GetMapping("")
    public ModelAndView messagePage() {
        return page().addComponent(new Overview("微信公众平台")
        )
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "素材管理", QXCMP_BACKEND_URL + "/weixin/material", "公众号配置", QXCMP_BACKEND_URL + "/weixin/mp", "微信支付配置", QXCMP_BACKEND_URL + "/weixin/pay");
    }
}
