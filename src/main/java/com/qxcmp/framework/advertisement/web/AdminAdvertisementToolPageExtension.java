package com.qxcmp.framework.advertisement.web;

import com.qxcmp.framework.core.web.AdminToolPageExtension;
import org.springframework.stereotype.Component;

import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP_BACKEND_URL;

/**
 * @author Aaric
 */
@Component
public class AdminAdvertisementToolPageExtension implements AdminToolPageExtension {
    @Override
    public String getIcon() {
        return "";
    }

    @Override
    public String getTitle() {
        return "广告管理";
    }

    @Override
    public String getUrl() {
        return QXCMP_BACKEND_URL + "/advertisement";
    }
}
