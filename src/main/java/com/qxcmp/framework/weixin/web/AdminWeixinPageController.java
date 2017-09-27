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
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.*;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/weixin")
@RequiredArgsConstructor
public class AdminWeixinPageController extends QXCMPBackendController {

    @GetMapping("")
    public ModelAndView messagePage() {
        return page().addComponent(new Overview("微信公众平台")
                .addComponent(convertToTable(stringObjectMap -> {
                    stringObjectMap.put("App ID", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
                    stringObjectMap.put("App Secret", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SECRET).orElse(""));
                    stringObjectMap.put("Token", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_TOKEN).orElse(""));
                    stringObjectMap.put("AES Key", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_AES_KEY).orElse(""));
                    stringObjectMap.put("网页授权回调链接", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
                    stringObjectMap.put("调试模式", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_DEBUG).orElse(""));
                    stringObjectMap.put("欢迎语", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE).orElse(""));
                    stringObjectMap.put("是否开启微信支付", systemConfigService.getBoolean(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE).orElse(SYSTEM_CONFIG_WECHAT_PAYMENT_ENABLE_DEFAULT_VALUE));
                    stringObjectMap.put("商户号", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_MCH_ID).orElse(""));
                    stringObjectMap.put("商户密钥", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_MCH_KEY).orElse(""));
                    stringObjectMap.put("子商户公众号ID", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUB_APP_ID).orElse(""));
                    stringObjectMap.put("子商户号", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SUB_MCH_ID).orElse(""));
                    stringObjectMap.put("支付结果通知Url", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_NOTIFY_URL).orElse(""));
                    stringObjectMap.put("apiclient_cert.p12的文件的绝对路径", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_KEY_PATH).orElse(""));
                    stringObjectMap.put("支付方式", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE).orElse(""));
                })))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "微信公众平台")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "素材管理", QXCMP_BACKEND_URL + "/weixin/material", "公众号配置", QXCMP_BACKEND_URL + "/weixin/mp", "微信支付配置", QXCMP_BACKEND_URL + "/weixin/pay");
    }
}
