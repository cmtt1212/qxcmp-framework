package com.qxcmp.framework.web.view.components.weixin;

import com.qxcmp.framework.finance.DepositOrder;
import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class WeixinPayScript extends AbstractComponent {

    private Map<String, String> payInfo;

    private DepositOrder depositOrder;

    public WeixinPayScript(Map<String, String> payInfo, DepositOrder depositOrder) {
        this.payInfo = payInfo;
        this.depositOrder = depositOrder;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/components/weixin/weixin-mp";
    }

    @Override
    public String getFragmentName() {
        return "weixin-pay-script";
    }
}
