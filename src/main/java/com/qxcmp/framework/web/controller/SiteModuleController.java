package com.qxcmp.framework.web.controller;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.config.SystemDictionaryItem;
import com.qxcmp.framework.config.SystemDictionaryItemService;
import com.qxcmp.framework.config.SystemDictionaryService;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.domain.ImageService;
import com.qxcmp.framework.view.dictionary.DictionaryView;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;

/**
 * 网站配置模块页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/site")
@RequiredArgsConstructor
public class SiteModuleController extends QXCMPBackendController2 {

    private static final List<String> WATERMARK_POSITIONS = ImmutableList.of("左上", "中上", "右上", "左中", "居中", "右中", "左下", "中下", "右下");

    private final ImageService imageService;

    private final AccountService accountService;

    private final SystemDictionaryService systemDictionaryService;

    private final SystemDictionaryItemService systemDictionaryItemService;

    @GetMapping("/config")
    public ModelAndView configGet() {
        final AdminSiteConfigForm form = new AdminSiteConfigForm();

        form.setDomain(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_DOMAIN).orElse(""));
        form.setTitle(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_TITLE).orElse(""));
        form.setKeywords(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_KEYWORDS).orElse(""));
        form.setDescription(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_DESCRIPTION).orElse(""));

        return builder().setTitle("站点配置")
                .setFormView(form)
                .addNavigation("网站配置", Navigation.Type.NORMAL, "网站设置")
                .build();
    }

    @PostMapping("/config")
    public ModelAndView configPost(@Valid @ModelAttribute("object") AdminSiteConfigForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改网站配置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_DOMAIN, form.getDomain());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_TITLE, form.getTitle());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_KEYWORDS, form.getKeywords());
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_DESCRIPTION, form.getDescription());

            if (StringUtils.isNotBlank(form.getLogoFile().getOriginalFilename())) {
                try {
                    String fileType = FilenameUtils.getExtension(form.getLogoFile().getOriginalFilename());
                    imageService.store(form.getLogoFile().getInputStream(), fileType, 64, 64).ifPresent(image ->
                            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_LOGO, String.format("/api/image/%s.%s", image.getId(), fileType)));
                } catch (IOException e) {
                    throw new ActionException("上传LOGO失败", e);
                }
            }

            if (StringUtils.isNotBlank(form.getFaviconFile().getOriginalFilename())) {
                try {
                    String fileType = FilenameUtils.getExtension(form.getFaviconFile().getOriginalFilename());
                    imageService.store(form.getFaviconFile().getInputStream(), fileType, 32, 32).ifPresent(image ->
                            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SITE_FAVICON, String.format("/api/image/%s.%s", image.getId(), fileType)));
                } catch (IOException e) {
                    throw new ActionException("上传图标失败", e);
                }
            }
        }).build();
    }

    @GetMapping("/account")
    public ModelAndView account(final AdminSiteAccountForm form) {

        form.setEnableUsername(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false));
        form.setEnableEmail(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false));
        form.setEnablePhone(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false));
        form.setEnableInvite(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE).orElse(false));

        return builder().setTitle("账户注册配置")
                .setFormView(form)
                .addNavigation("账户注册配置", Navigation.Type.NORMAL, "网站设置")
                .build();
    }

    @PostMapping("/account")
    public ModelAndView account(@Valid @ModelAttribute(FORM_OBJECT) final AdminSiteAccountForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setTitle("账户注册配置")
                    .setFormView(form)
                    .addNavigation("账户注册配置", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        return action("修改账户注册配置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME, String.valueOf(form.isEnableUsername()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL, String.valueOf(form.isEnableEmail()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE, String.valueOf(form.isEnablePhone()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_INVITE, String.valueOf(form.isEnableInvite()));

            accountService.loadConfig();
        }).build();
    }

    @GetMapping("/dictionary")
    public ModelAndView systemDict(Pageable pageable) {
        return builder().setTitle("系统字典")
                .setTableView(pageable, systemDictionaryService)
                .addNavigation("系统字典", Navigation.Type.NORMAL, "网站设置")
                .build();
    }

    @GetMapping("/dictionary/{name}")
    public ModelAndView systemDict(@PathVariable String name) {
        return systemDictionaryService.findOne(name).map(systemDictionary -> {
            DictionaryView.DictionaryViewBuilder dictionaryViewBuilder = DictionaryView.builder();

            for (int i = 0; i < systemDictionary.getItems().size(); i++) {
                dictionaryViewBuilder.dictionary("字典项" + (i + 1), systemDictionary.getItems().get(i).getName());
            }

            return builder().setTitle("系统字典详情")
                    .setResult("系统字典", systemDictionary.getName())
                    .addDivider()
                    .addDictionaryView(dictionaryViewBuilder.build())
                    .addNavigation("系统字典", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }).orElse(error(HttpStatus.NOT_FOUND, "系统字典不存在").build());
    }

    @GetMapping("/dictionary/{name}/edit")
    public ModelAndView systemDictEdit(@PathVariable String name) {
        return systemDictionaryService.findOne(name).map(systemDictionary -> {
            final AdminSiteDictionaryForm form = new AdminSiteDictionaryForm();
            form.setName(systemDictionary.getName());
            form.setItems(systemDictionary.getItems());
            return builder().setTitle("修改系统字典")
                    .setResult("修改系统字典 - " + systemDictionary.getName(), "修改以后立即对平台生效")
                    .addDivider()
                    .setFormView(form)
                    .addNavigation("系统字典", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }).orElse(error(HttpStatus.NOT_FOUND, "系统字典不存在").build());
    }

    @PostMapping("/dictionary")
    public ModelAndView systemDictPost(
            @RequestParam(value = "add_items", required = false) String addItems,
            @RequestParam(value = "remove_items", required = false) String removeItems,
            @Valid @ModelAttribute(FORM_OBJECT) AdminSiteDictionaryForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setTitle("修改系统字典").setTitle("修改系统字典")
                    .setResult("修改系统字典 - " + form.getName(), "修改以后立即对平台生效")
                    .addDivider()
                    .setFormView(form)
                    .addNavigation("系统字典", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        if (StringUtils.isNotEmpty(addItems)) {
            form.getItems().add(new SystemDictionaryItem());
            return builder().setTitle("修改系统字典").setTitle("修改系统字典")
                    .setResult("修改系统字典 - " + form.getName(), "修改以后立即对平台生效")
                    .addDivider()
                    .setFormView(form)
                    .addNavigation("系统字典", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        if (StringUtils.isNotEmpty(removeItems)) {
            try {
                Integer itemIndex = Integer.valueOf(removeItems);
                form.getItems().remove(itemIndex.intValue());
            } catch (NumberFormatException ignored) {

            }
            return builder().setTitle("修改系统字典").setTitle("修改系统字典")
                    .setResult("修改系统字典 - " + form.getName(), "修改以后立即对平台生效")
                    .addDivider()
                    .setFormView(form)
                    .addNavigation("系统字典", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        return action("修改系统字典", context -> systemDictionaryService.update(form.getName(), systemDictionary -> {
            systemDictionary.getItems().forEach(systemDictionaryItem -> systemDictionaryItemService.remove(systemDictionaryItem.getId()));
            form.getItems().forEach(systemDictionaryItem -> {
                systemDictionaryItem.setId(null);
                systemDictionaryItem.setParent(systemDictionary);
                systemDictionaryItemService.save(systemDictionaryItem);
            });
            systemDictionary.setItems(form.getItems());
        })).build();
    }

    @GetMapping("/task")
    public ModelAndView taskGet(final AdminSiteTaskForm form) {
        form.setThreadPoolSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE_DEFAULT_VALUE));
        form.setMaxPoolSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE_DEFAULT_VALUE));
        form.setQueueSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY_DEFAULT_VALUE));

        return builder().setTitle("任务调度配置")
                .setFormView(form)
                .addNavigation("任务调度配置", Navigation.Type.NORMAL, "网站设置")
                .build();
    }

    @PostMapping("/task")
    public ModelAndView taskPost(@Valid @ModelAttribute(FORM_OBJECT) final AdminSiteTaskForm form, BindingResult bindingResult) {

        if (form.getThreadPoolSize() >= form.getMaxPoolSize()) {
            bindingResult.rejectValue("threadPoolSize", "", "线程池大小必须小于最大线程池大小");
        }

        if (bindingResult.hasErrors()) {
            return builder().setTitle("任务调度配置")
                    .setFormView(form)
                    .addNavigation("任务调度配置", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        return action("修改任务调度配置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_CORE_POOL_SIZE, String.valueOf(form.getThreadPoolSize()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_MAX_POOL_SIZE, String.valueOf(form.getMaxPoolSize()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_TASK_EXECUTOR_QUEUE_CAPACITY, String.valueOf(form.getQueueSize()));
        }).build();
    }

    @GetMapping("/session")
    public ModelAndView sessionGet(final AdminSiteSessionForm form) {
        form.setSessionTimeout(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_TIMEOUT).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_TIMEOUT_DEFAULT_VALUE));
        form.setMaxSessionCount(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT_DEFAULT_VALUE));
        form.setPreventLogin(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN_DEFAULT_VALUE));

        return builder().setTitle("系统会话配置")
                .setFormView(form)
                .addNavigation("系统会话配置", Navigation.Type.NORMAL, "网站设置")
                .build();
    }

    @PostMapping("/session")
    public ModelAndView sessionPost(@Valid @ModelAttribute(FORM_OBJECT) final AdminSiteSessionForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return builder().setTitle("系统会话配置")
                    .setFormView(form)
                    .addNavigation("系统会话配置", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        return action("修改系统会话配置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_TIMEOUT, String.valueOf(form.getSessionTimeout()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_ACTIVE_COUNT, String.valueOf(form.getMaxSessionCount()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_SESSION_MAX_PREVENT_LOGIN, String.valueOf(form.isPreventLogin()));
        }).build();
    }

    @GetMapping("/watermark")
    public ModelAndView watermark(final AdminSiteWatermarkForm form) {

        form.setEnable(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE_DEFAULT_VALUE));
        form.setName(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_NAME).orElse(qxcmpConfiguration.getTitle()));
        form.setPosition(WATERMARK_POSITIONS.get(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION_DEFAULT_VALUE)));
        form.setFontSize(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE_DEFAULT_VALUE));

        return builder().setTitle("水印设置")
                .setFormView(form, WATERMARK_POSITIONS)
                .addNavigation("水印设置", Navigation.Type.NORMAL, "网站设置")
                .build();
    }

    @PostMapping("/watermark")
    public ModelAndView watermark(@Valid @ModelAttribute(FORM_OBJECT) final AdminSiteWatermarkForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return builder().setTitle("水印设置")
                    .setFormView(form, WATERMARK_POSITIONS)
                    .addNavigation("水印设置", Navigation.Type.NORMAL, "网站设置")
                    .build();
        }

        return action("修改水平设置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_ENABLE, String.valueOf(form.isEnable()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_NAME, String.valueOf(form.getName()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_POSITION, String.valueOf(WATERMARK_POSITIONS.indexOf(form.getPosition())));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_IMAGE_WATERMARK_FONT_SIZE, String.valueOf(form.getFontSize()));
        }).build();
    }
}
