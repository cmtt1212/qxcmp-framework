package com.qxcmp.framework.site.web;

import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.*;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/site")
public class SiteAdminPageController extends QXCMPBackendController {

    @GetMapping("/setting")
    public ModelAndView settingPage(final AdminSiteSettingForm form) {

        form.setLogo(systemConfigService.getString(SYSTEM_CONFIG_SITE_LOGO).orElse(""));
        form.setFavicon(systemConfigService.getString(SYSTEM_CONFIG_SITE_FAVICON).orElse(""));
        form.setDomain(systemConfigService.getString(SYSTEM_CONFIG_SITE_DOMAIN).orElse(""));
        form.setTitle(systemConfigService.getString(SYSTEM_CONFIG_SITE_TITLE).orElse(""));
        form.setKeywords(systemConfigService.getString(SYSTEM_CONFIG_SITE_KEYWORDS).orElse(""));
        form.setDescription(systemConfigService.getString(SYSTEM_CONFIG_SITE_DESCRIPTION).orElse(""));

        return page()
                .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                        .addComponent(new PageHeader(HeaderType.H2, "网站配置").setDividing())
                        .addComponent(convertToForm(form))
                ))).build();
    }

    @PostMapping("/setting")
    public ModelAndView settingPage(@Valid final AdminSiteSettingForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                            .addComponent(new PageHeader(HeaderType.H2, "网站配置").setDividing())
                            .addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
                    ))).build();
        }

        systemConfigService.update(SYSTEM_CONFIG_SITE_LOGO, form.getLogo());
        systemConfigService.update(SYSTEM_CONFIG_SITE_FAVICON, form.getFavicon());
        systemConfigService.update(SYSTEM_CONFIG_SITE_DOMAIN, form.getDomain());
        systemConfigService.update(SYSTEM_CONFIG_SITE_TITLE, form.getTitle());
        systemConfigService.update(SYSTEM_CONFIG_SITE_KEYWORDS, form.getKeywords());
        systemConfigService.update(SYSTEM_CONFIG_SITE_DESCRIPTION, form.getDescription());

        return redirect(QXCMP_BACKEND_URL + "/site/setting");
    }
}
