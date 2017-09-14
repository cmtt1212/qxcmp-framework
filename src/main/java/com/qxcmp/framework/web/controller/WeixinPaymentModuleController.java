package com.qxcmp.framework.web.controller;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.AdminFinancePaymentWeixinSettingsForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;

/**
 * 微信支付模块后台页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/finance/payment/weixin")
@RequiredArgsConstructor
public class WeixinPaymentModuleController extends QXCMPBackendController2 {

    public static final List<String> SUPPORT_PAYMENT = ImmutableList.of("NATIVE", "JSAPI");

    private final WxPayConfig wxPayConfig;

    /**
     * 获取钱包充值页面
     * <p>
     * 该页面作为整个平台资金来源的唯一入口，即平台的所有消费必须先充值到钱包以后才能进行消息
     *
     * @return 钱包充值页面
     */
    @GetMapping("/settings")
    public ModelAndView wechatSetting(final AdminFinancePaymentWeixinSettingsForm form) {
        form.setEnable(systemConfigService.getBoolean(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE).orElse(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE_DEFAULT_VALUE));
        form.setTradeType(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE).orElse(SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE_DEFAULT_VALUE));
        form.setAppId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
        form.setMchId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_MCH_ID).orElse(""));
        form.setMchKey(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_MCH_KEY).orElse(""));
        form.setSubAppId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUB_APP_ID).orElse(""));
        form.setSubMchId(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUB_MCH_ID).orElse(""));
        form.setNotifyUrl(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_NOTIFY_URL).orElse(""));
        form.setKeyPath(systemConfigService.getString(SYSTEM_CONFIG_WECHAT_KEY_PATH).orElse(""));
        return builder().setTitle("微信支付")
                .setFormView(form, SUPPORT_PAYMENT)
                .addNavigation("微信支付", Navigation.Type.NORMAL, "公众号设置")
                .build();
    }

    @PostMapping("/settings")
    public ModelAndView servicePost(@Valid @ModelAttribute(FORM_OBJECT) AdminFinancePaymentWeixinSettingsForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form, SUPPORT_PAYMENT).build();
        }

        return action("修改微信公众号配置", context -> {
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE, String.valueOf(form.isEnable()));
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE, form.getTradeType());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_APP_ID, form.getAppId());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_MCH_ID, form.getMchId());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_MCH_KEY, form.getMchKey());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_SUB_APP_ID, form.getSubAppId());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_SUB_MCH_ID, form.getSubMchId());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_NOTIFY_URL, form.getNotifyUrl());
            systemConfigService.update(SYSTEM_CONFIG_WECHAT_KEY_PATH, form.getKeyPath());
            systemConfigService.update(SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN, systemConfigService.getBoolean(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE).orElse(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE_DEFAULT_VALUE).toString());

            wxPayConfig.setAppId(form.getAppId());
            wxPayConfig.setMchId(form.getMchId());
            wxPayConfig.setMchKey(form.getMchKey());
            wxPayConfig.setSubAppId(form.getSubAppId());
            wxPayConfig.setSubMchId(form.getSubMchId());
            wxPayConfig.setNotifyUrl(form.getNotifyUrl());
            wxPayConfig.setKeyPath(form.getKeyPath());
        }).build();
    }
}
