package com.qxcmp.framework.finance.web;

import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.components.finance.DepositComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class FinancePageHelper {
    public AbstractComponent nextDepositComponent(SystemConfigService systemConfigService) {


        boolean supportWeixin = systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_WEIXIN_DEFAULT_VALUE);
        boolean supportAlipay = systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_ALIPAY).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_FINANCE_PAYMENT_SUPPORT_ALIPAY_DEFAULT_VALUE);

        String weixinTradeType = systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_WECHAT_PAYMENT_DEFAULT_TRADE_TYPE_DEFAULT_VALUE);
        String weixinActionUrl = "";

        if (StringUtils.equals(weixinTradeType, "NATIVE")) {
            weixinActionUrl = "/api/wxmp-cgi/pay/native";
        } else if (StringUtils.equals(weixinTradeType, "JSAPI")) {
            weixinActionUrl = "/api/wxmp-cgi/pay/mp";
        }

        return new DepositComponent();
    }
}
