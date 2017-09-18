package com.qxcmp.framework.core.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.ColumnCount;
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
public class SettingsAdminPageController extends QXCMPBackendController {

    private static final List<String> WATERMARK_POSITIONS = ImmutableList.of("左上", "中上", "右上", "左中", "居中", "右中", "左下", "中下", "右下");

    private final AccountService accountService;

    @GetMapping("/site")
    public ModelAndView sitePage(final AdminSettingsSiteForm form) {

        form.setLogo(systemConfigService.getString(SYSTEM_CONFIG_SITE_LOGO).orElse(SYSTEM_CONFIG_SITE_LOGO_DEFAULT_VALUE));
        form.setFavicon(systemConfigService.getString(SYSTEM_CONFIG_SITE_FAVICON).orElse(SYSTEM_CONFIG_SITE_FAVICON_DEFAULT_VALUE));
        form.setDomain(systemConfigService.getString(SYSTEM_CONFIG_SITE_DOMAIN).orElse(""));
        form.setTitle(systemConfigService.getString(SYSTEM_CONFIG_SITE_TITLE).orElse(""));
        form.setKeywords(systemConfigService.getString(SYSTEM_CONFIG_SITE_KEYWORDS).orElse(""));
        form.setDescription(systemConfigService.getString(SYSTEM_CONFIG_SITE_DESCRIPTION).orElse(""));

        return page()
                .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                        .addComponent(convertToForm(form))
                ))).build();
    }

    @PostMapping("/site")
    public ModelAndView sitePage(@Valid final AdminSettingsSiteForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                            .addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
                    ))).build();
        }

        return submitForm(form, (context) -> {
            systemConfigService.update(SYSTEM_CONFIG_SITE_LOGO, form.getLogo());
            systemConfigService.update(SYSTEM_CONFIG_SITE_FAVICON, form.getFavicon());
            systemConfigService.update(SYSTEM_CONFIG_SITE_DOMAIN, form.getDomain());
            systemConfigService.update(SYSTEM_CONFIG_SITE_TITLE, form.getTitle());
            systemConfigService.update(SYSTEM_CONFIG_SITE_KEYWORDS, form.getKeywords());
            systemConfigService.update(SYSTEM_CONFIG_SITE_DESCRIPTION, form.getDescription());
        });
    }

    @GetMapping("/account")
    public ModelAndView accountPage(final AdminSettingsAccountForm form) {

        form.setEnableUsername(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false));
        form.setEnableEmail(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false));
        form.setEnablePhone(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false));
        form.setEnableInvite(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE).orElse(false));

        return page()
                .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                        .addComponent(convertToForm(form))
                ))).build();
    }

    @PostMapping("/account")
    public ModelAndView sitePage(@Valid final AdminSettingsAccountForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                            .addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
                    ))).build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME, String.valueOf(form.isEnableUsername()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL, String.valueOf(form.isEnableEmail()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE, String.valueOf(form.isEnablePhone()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE, String.valueOf(form.isEnableInvite()));
            accountService.loadConfig();
        });
    }

    @GetMapping("/watermark")
    public ModelAndView watermarkPage(final AdminSettingsWatermarkForm form) {

        form.setEnable(systemConfigService.getBoolean(SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE).orElse(SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE_DEFAULT_VALUE));
        form.setName(systemConfigService.getString(SYSTEM_CONFIG_IMAGE_WATERMARK_NAME).orElse(siteService.getTitle()));
        form.setPosition(WATERMARK_POSITIONS.get(systemConfigService.getInteger(SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION).orElse(SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION_DEFAULT_VALUE)));
        form.setFontSize(systemConfigService.getInteger(SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE).orElse(SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE_DEFAULT_VALUE));

        return page()
                .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                        .addComponent(convertToForm(form))
                ))).addObject("selection_items_position", WATERMARK_POSITIONS).build();
    }

    @PostMapping("/watermark")
    public ModelAndView watermarkPage(@Valid final AdminSettingsWatermarkForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page()
                    .addComponent(new VerticallyDividedGrid().setVerticallyPadded().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(new Segment()
                            .addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
                    ))).addObject("selection_items_position", WATERMARK_POSITIONS).build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE, String.valueOf(form.isEnable()));
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_NAME, form.getName());
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION, String.valueOf(WATERMARK_POSITIONS.indexOf(form.getPosition())));
            systemConfigService.update(SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE, String.valueOf(form.getFontSize()));
        });
    }
}
