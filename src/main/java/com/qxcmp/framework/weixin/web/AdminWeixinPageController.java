package com.qxcmp.framework.weixin.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.views.Overview;
import com.qxcmp.framework.weixin.WechatService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.*;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/weixin")
@RequiredArgsConstructor
public class AdminWeixinPageController extends QXCMPBackendController {

    private final WxMpService wxMpService;

    private final WxMpConfigStorage wxMpConfigStorage;

    private final WechatService wechatService;

    @GetMapping("")
    public ModelAndView weixinPage() {
        return page().addComponent(new Overview("微信公众平台")
                .addComponent(convertToTable(stringObjectMap -> {
                    stringObjectMap.put("App ID", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
                    stringObjectMap.put("App Secret", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_SECRET).orElse(""));
                    stringObjectMap.put("Token", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_TOKEN).orElse(""));
                    stringObjectMap.put("AES Key", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_AES_KEY).orElse(""));
                    stringObjectMap.put("网页授权回调链接", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL).orElse(""));
                    stringObjectMap.put("网页授权链接", systemConfigService.getString(SYSTEM_CONFIG_WECHAT_OAUTH2_AUTHORIZATION_URL).orElse(""));
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

    @GetMapping("/mp")
    public ModelAndView weixinMpPage(final AdminWeixinMpForm form) {

        form.setDebug(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_DEBUG).orElse(false));
        form.setAppId(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID).orElse(""));
        form.setSecret(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SECRET).orElse(""));
        form.setToken(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_TOKEN).orElse(""));
        form.setAesKey(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_AES_KEY).orElse(""));
        form.setOauth2Url(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL).orElse(""));
        form.setSubscribeMessage(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE).orElse(""));

        return page()
                .addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "微信公众平台", QXCMP_BACKEND_URL + "/weixin", "公众号配置")
                .setVerticalMenu(getVerticalMenu("公众号配置"))
                .build();
    }

    @PostMapping("/mp")
    public ModelAndView weixinMpPage(@Valid final AdminWeixinMpForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "微信公众平台", QXCMP_BACKEND_URL + "/weixin", "公众号配置")
                    .setVerticalMenu(getVerticalMenu("公众号配置"))
                    .build();
        }

        return submitForm(form, context -> {

            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_DEBUG, String.valueOf(form.isDebug()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_APP_ID, form.getAppId());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SECRET, form.getSecret());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_TOKEN, form.getToken());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_AES_KEY, form.getAesKey());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_CALLBACK_URL, form.getOauth2Url());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_SUBSCRIBE_WELCOME_MESSAGE, form.getSubscribeMessage());

            WxMpInMemoryConfigStorage configStorage = (WxMpInMemoryConfigStorage) wxMpConfigStorage;
            configStorage.setAppId(form.getAppId());
            configStorage.setSecret(form.getSecret());
            configStorage.setToken(form.getToken());
            configStorage.setAesKey(form.getAesKey());

            if (StringUtils.isNotBlank(form.getOauth2Url())) {
                try {
                    String oauth2Url = wxMpService.oauth2buildAuthorizationUrl(form.getOauth2Url(), WxConsts.OAUTH2_SCOPE_USER_INFO, null);
                    context.put("oauth2Url", oauth2Url);
                    systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_OAUTH2_AUTHORIZATION_URL, oauth2Url);
                } catch (Exception e) {
                    throw new ActionException("Can't build Oauth2 Url", e);
                }
            }
        }, (stringObjectMap, overview) -> overview.addComponent(convertToTable(map -> map.put("网页授权链接", stringObjectMap.get("oauth2Url")))));
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "素材管理", QXCMP_BACKEND_URL + "/weixin/material", "公众号配置", QXCMP_BACKEND_URL + "/weixin/mp", "微信支付配置", QXCMP_BACKEND_URL + "/weixin/pay");
    }
}
