package com.qxcmp.framework.core.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.list.item.TextItem;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(QXCMP_BACKEND_URL + "/settings")
@RequiredArgsConstructor
public class AdminSettingsPageController extends QXCMPBackendController {

    private static final List<String> WATERMARK_POSITIONS = ImmutableList.of("左上", "中上", "右上", "左中", "居中", "右中", "左下", "中下", "右下");

    private final AccountService accountService;

    @GetMapping("")
    public ModelAndView settingsPage() {
        return page().addComponent(new Segment().setAlignment(Alignment.CENTER)
                .addComponent(new PageHeader(HeaderType.H1, "系统设置"))
                .addComponent(new com.qxcmp.framework.web.view.elements.list.List().setSelection()
                        .addItem(new TextItem("网站配置").setUrl(QXCMP_BACKEND_URL + "/settings/site"))
                        .addItem(new TextItem("账户配置").setUrl(QXCMP_BACKEND_URL + "/settings/account"))
                ))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置")
                .build();
    }

    @GetMapping("/site")
    public ModelAndView sitePage(final AdminSettingsSiteForm form) {

        form.setLogo(systemConfigService.getString(SYSTEM_CONFIG_SITE_LOGO).orElse(SYSTEM_CONFIG_SITE_LOGO_DEFAULT_VALUE));
        form.setFavicon(systemConfigService.getString(SYSTEM_CONFIG_SITE_FAVICON).orElse(SYSTEM_CONFIG_SITE_FAVICON_DEFAULT_VALUE));
        form.setDomain(systemConfigService.getString(SYSTEM_CONFIG_SITE_DOMAIN).orElse(""));
        form.setTitle(systemConfigService.getString(SYSTEM_CONFIG_SITE_TITLE).orElse(""));
        form.setKeywords(systemConfigService.getString(SYSTEM_CONFIG_SITE_KEYWORDS).orElse(""));
        form.setDescription(systemConfigService.getString(SYSTEM_CONFIG_SITE_DESCRIPTION).orElse(""));

        form.setWatermarkEnabled(systemConfigService.getBoolean(SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE).orElse(SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE_DEFAULT_VALUE));
        form.setWatermarkName(systemConfigService.getString(SYSTEM_CONFIG_IMAGE_WATERMARK_NAME).orElse(siteService.getTitle()));
        form.setWatermarkPosition(WATERMARK_POSITIONS.get(systemConfigService.getInteger(SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION).orElse(SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION_DEFAULT_VALUE)));
        form.setWatermarkFontSize(systemConfigService.getInteger(SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE).orElse(SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE_DEFAULT_VALUE));

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置", QXCMP_BACKEND_URL + "/settings", "网站配置")
                .setVerticalMenu(getVerticalMenu("网站配置"))
                .addObject("selection_items_position", WATERMARK_POSITIONS)
                .build();
    }

    @PostMapping("/site")
    public ModelAndView sitePage(@Valid final AdminSettingsSiteForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置", QXCMP_BACKEND_URL + "/settings", "网站配置")
                    .setVerticalMenu(getVerticalMenu("网站配置"))
                    .addObject("selection_items_position", WATERMARK_POSITIONS)
                    .build();
        }

        return submitForm(form, (context) -> {
            systemConfigService.update(SYSTEM_CONFIG_SITE_LOGO, form.getLogo());
            systemConfigService.update(SYSTEM_CONFIG_SITE_FAVICON, form.getFavicon());
            systemConfigService.update(SYSTEM_CONFIG_SITE_DOMAIN, form.getDomain());
            systemConfigService.update(SYSTEM_CONFIG_SITE_TITLE, form.getTitle());
            systemConfigService.update(SYSTEM_CONFIG_SITE_KEYWORDS, form.getKeywords());
            systemConfigService.update(SYSTEM_CONFIG_SITE_DESCRIPTION, form.getDescription());

            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE, String.valueOf(form.isWatermarkEnabled()));
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_NAME, form.getWatermarkName());
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION, String.valueOf(WATERMARK_POSITIONS.indexOf(form.getWatermarkPosition())));
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE, String.valueOf(form.getWatermarkFontSize()));
        });
    }

    @GetMapping("/account")
    public ModelAndView accountPage(final AdminSettingsAccountForm form) {

        form.setEnableUsername(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false));
        form.setEnableEmail(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false));
        form.setEnablePhone(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false));
        form.setEnableInvite(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE).orElse(false));

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置", QXCMP_BACKEND_URL + "/settings", "账户配置")
                .setVerticalMenu(getVerticalMenu("账户配置"))
                .build();
    }

    @PostMapping("/account")
    public ModelAndView sitePage(@Valid final AdminSettingsAccountForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置", QXCMP_BACKEND_URL + "/settings", "账户配置")
                    .setVerticalMenu(getVerticalMenu("账户配置"))
                    .build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME, String.valueOf(form.isEnableUsername()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL, String.valueOf(form.isEnableEmail()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE, String.valueOf(form.isEnablePhone()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE, String.valueOf(form.isEnableInvite()));
            accountService.loadConfig();
        });
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "网站配置", QXCMP_BACKEND_URL + "/settings/site", "账户配置", QXCMP_BACKEND_URL + "/settings/account");
    }
}
