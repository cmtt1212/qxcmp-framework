package com.qxcmp.framework.web.view.support.utils;

import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 视图生成工具
 *
 * @author Aaric
 */
@Component
@RequiredArgsConstructor
public class ViewHelper {

    private final FormHelper formHelper;

    private final TableHelper tableHelper;

    private final OverviewHelper overviewHelper;

    public Overview nextOverview(Icon icon, String title, String subTitle) {
        return overviewHelper.nextOverview(icon, title, subTitle);
    }

    public Overview nextInfoOverview(String title, String subTitle) {
        return nextOverview(new Icon("info circle"), title, subTitle);
    }

    public Overview nextWarningOverview(String title, String subTitle) {
        return nextOverview(new Icon("warning circle").setColor(Color.ORANGE), title, subTitle);
    }
}
