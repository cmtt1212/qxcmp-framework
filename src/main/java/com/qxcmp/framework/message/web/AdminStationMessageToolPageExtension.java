package com.qxcmp.framework.message.web;

import com.qxcmp.framework.core.web.tool.AdminToolPageExtension;
import org.springframework.stereotype.Component;

import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP_BACKEND_URL;

/**
 * @author Aaric
 */
@Component
public class AdminStationMessageToolPageExtension implements AdminToolPageExtension {
    @Override
    public String getIcon() {
        return "";
    }

    @Override
    public String getTitle() {
        return "站内信";
    }

    @Override
    public String getUrl() {
        return QXCMP_BACKEND_URL + "/inbox";
    }
}
