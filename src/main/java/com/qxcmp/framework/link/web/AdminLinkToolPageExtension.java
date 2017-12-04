package com.qxcmp.framework.link.web;

import com.qxcmp.framework.web.extension.AdminToolPageExtension;
import org.springframework.stereotype.Component;

import static com.qxcmp.framework.core.QxcmpConfiguration.QXCMP_BACKEND_URL;

/**
 * @author Aaric
 */
@Component
public class AdminLinkToolPageExtension implements AdminToolPageExtension {
    @Override
    public String getIcon() {
        return "";
    }

    @Override
    public String getTitle() {
        return "链接管理";
    }

    @Override
    public String getUrl() {
        return QXCMP_BACKEND_URL + "/link";
    }
}
