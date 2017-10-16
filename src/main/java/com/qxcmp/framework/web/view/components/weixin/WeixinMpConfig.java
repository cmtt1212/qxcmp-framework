package com.qxcmp.framework.web.view.components.weixin;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;

import java.util.List;

public class WeixinMpConfig extends AbstractComponent {

    @Getter
    private List<String> apiList;

    public WeixinMpConfig(List<String> apiList) {
        this.apiList = apiList;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/components/weixin/weixin-mp";
    }

    @Override
    public String getFragmentName() {
        return "config";
    }
}
