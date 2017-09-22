package com.qxcmp.framework.core.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
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
    public ModelAndView sitePage(final AdminSettingsForm form) {

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

        form.setAccountEnableUsername(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false));
        form.setAccountEnableEmail(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false));
        form.setAccountEnablePhone(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false));
        form.setAccountEnableInvite(systemConfigService.getBoolean(SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE).orElse(false));

        form.setThreadPoolSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE_DEFAULT_VALUE));
        form.setMaxPoolSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE_DEFAULT_VALUE));
        form.setQueueSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY_DEFAULT_VALUE));

        form.setSessionTimeout(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_TIMEOUT).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_TIMEOUT_DEFAULT_VALUE));
        form.setMaxSessionCount(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT_DEFAULT_VALUE));
        form.setPreventLogin(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN_DEFAULT_VALUE));

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置")
                .addObject("selection_items_position", WATERMARK_POSITIONS)
                .build();
    }

    @PostMapping("")
    public ModelAndView sitePage(@Valid final AdminSettingsForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "系统设置")
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

            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME, String.valueOf(form.isAccountEnableUsername()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL, String.valueOf(form.isAccountEnableEmail()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE, String.valueOf(form.isAccountEnablePhone()));
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE, String.valueOf(form.isAccountEnableInvite()));

            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE, String.valueOf(form.getThreadPoolSize()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE, String.valueOf(form.getMaxPoolSize()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY, String.valueOf(form.getQueueSize()));

            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_TIMEOUT, String.valueOf(form.getSessionTimeout()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT, String.valueOf(form.getMaxSessionCount()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN, String.valueOf(form.isPreventLogin()));

            accountService.loadConfig();
        });
    }

}
