package com.qxcmp.framework.finance.web;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.web.QxcmpController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_FINANCE;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.NAVIGATION_ADMIN_FINANCE_WEIXIN_SETTINGS;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/finance")
@RequiredArgsConstructor
public class AdminFinancePageController extends QxcmpController {

    private static final List<String> SUPPORT_WEIXIN_PAYMENT = ImmutableList.of("NATIVE", "JSAPI");

    private final WxPayConfig wxPayConfig;

    @GetMapping("")
    public ModelAndView financePage() {
        return page()
                .setBreadcrumb("控制台", "", "财务管理")
                .setVerticalNavigation(NAVIGATION_ADMIN_FINANCE, "")
                .build();
    }

    @GetMapping("/weixin")
    public ModelAndView weixinPayPage(final AdminFinanceWeixinForm form) {

        form.setEnable(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE_DEFAULT_VALUE));
        form.setTradeType(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE_DEFAULT_VALUE));
        form.setAppId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
        form.setMchId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_MCH_ID).orElse(""));
        form.setMchKey(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_MCH_KEY).orElse(""));
        form.setNotifyUrl(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_NOTIFY_URL).orElse(""));
        form.setSubAppId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUB_APP_ID).orElse(""));
        form.setSubMchId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUB_MCH_ID).orElse(""));
        form.setKeyPath(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_KEY_PATH).orElse(""));

        return page()
                .addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", "", "财务管理", "finance", "微信支付配置")
                .setVerticalNavigation(NAVIGATION_ADMIN_FINANCE, NAVIGATION_ADMIN_FINANCE_WEIXIN_SETTINGS)
                .addObject("selection_items_tradeType", SUPPORT_WEIXIN_PAYMENT)
                .build();
    }

    @PostMapping("/weixin")
    public ModelAndView weixinPayPage(@Valid final AdminFinanceWeixinForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "财务管理", "finance", "微信支付配置")
                    .setVerticalNavigation(NAVIGATION_ADMIN_FINANCE, NAVIGATION_ADMIN_FINANCE_WEIXIN_SETTINGS)
                    .addObject("selection_items_tradeType", SUPPORT_WEIXIN_PAYMENT)
                    .build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE, String.valueOf(form.isEnable()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE, form.getTradeType());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID, form.getAppId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_MCH_ID, form.getMchId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_MCH_KEY, form.getMchKey());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUB_APP_ID, form.getSubAppId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUB_MCH_ID, form.getSubMchId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_NOTIFY_URL, form.getNotifyUrl());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_KEY_PATH, form.getKeyPath());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN, systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE_DEFAULT_VALUE).toString());

            wxPayConfig.setAppId(form.getAppId());
            wxPayConfig.setMchId(form.getMchId());
            wxPayConfig.setMchKey(form.getMchKey());
            wxPayConfig.setSubAppId(form.getSubAppId());
            wxPayConfig.setSubMchId(form.getSubMchId());
            wxPayConfig.setNotifyUrl(form.getNotifyUrl());
            wxPayConfig.setKeyPath(form.getKeyPath());
        }, (stringObjectMap, overview) -> overview.addLink("返回", QXCMP_BACKEND_URL + "/finance/weixin"));
    }
}
