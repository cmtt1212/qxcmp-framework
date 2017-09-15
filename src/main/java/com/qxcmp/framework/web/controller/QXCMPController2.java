package com.qxcmp.framework.web.controller;

import com.google.common.collect.Lists;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.config.SystemConfigService;
import com.qxcmp.framework.core.QXCMPSystemConfigConfiguration;
import com.qxcmp.framework.domain.RedeemKey;
import com.qxcmp.framework.domain.RedeemKeyService;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.AdminRedeemGenerateForm;
import com.qxcmp.framework.web.form.AdminRedeemSettingsForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;

/**
 * 兑换码模块后台页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/redeem")
@RequiredArgsConstructor
public class QXCMPController2 extends QXCMPBackendController2 {

    private final RedeemKeyService redeemKeyService;

    private final SystemConfigService systemConfigService;

    @GetMapping
    public ModelAndView redeem() {
        return builder().setTitle("兑换码管理")
                .setResult("兑换码管理", "")
                .addNavigation(Navigation.Type.NORMAL, "兑换码管理")
                .build();
    }

    @GetMapping("/generate")
    public ModelAndView redeemGenerateGet(AdminRedeemGenerateForm form) {
        form.setDateExpired(new Date(System.currentTimeMillis() + 1000 * systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION_DEFAULT_VALUE)));
        form.setQuantity(1);
        return builder().setTitle("生成兑换码")
                .setFormView(form, getRedeemTypes())
                .addNavigation("生成兑换码", Navigation.Type.NORMAL, "兑换码管理")
                .build();
    }

    @PostMapping("/generate")
    public ModelAndView redeemGeneratePost(@Valid @ModelAttribute(FORM_OBJECT) AdminRedeemGenerateForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form, getRedeemTypes()).build();
        }

        List<RedeemKey> redeemKeys = Lists.newArrayList();

        return action("生成兑换码", context -> {
                    try {
                        for (int i = 0; i < form.getQuantity(); i++) {
                            redeemKeyService.create(() -> {
                                RedeemKey redeemKey = redeemKeyService.next();
                                redeemKey.setType(form.getType());
                                redeemKey.setContent(form.getContent());
                                redeemKey.setDateExpired(form.getDateExpired());
                                return redeemKey;
                            }).ifPresent(redeemKeys::add);
                        }
                    } catch (Exception e) {
                        throw new ActionException(e.getMessage());
                    }

                }, (context, modelAndViewBuilder) -> modelAndViewBuilder
                        .addDictionaryView(dictionaryViewBuilder -> {
                            dictionaryViewBuilder.dictionary("业务类型", form.getType());
                            dictionaryViewBuilder.dictionary("业务数据", form.getContent());

                            for (int i = 0; i < redeemKeys.size(); i++) {
                                dictionaryViewBuilder.dictionary(String.format("兑换码%d", i + 1), redeemKeys.get(i).getId());
                            }

                        })
                        .setResultNavigation("查看兑换码", QXCMP_BACKEND_URL + "/redeem/list", "继续生成", QXCMP_BACKEND_URL + "/redeem/generate")
        ).build();
    }

    @GetMapping("/settings")
    public ModelAndView settingsGet(final AdminRedeemSettingsForm form) {
        form.setEnable(systemConfigService.getBoolean(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_ENABLE).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_ENABLE_DEFAULT_VALUE));
        form.setType(systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_TYPE_LIST).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_TYPE_LIST_DEFAULT_VALUE));
        form.setExpireDuration(systemConfigService.getInteger(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION_DEFAULT_VALUE));
        return builder().setTitle("兑换码设置")
                .setFormView(form)
                .addNavigation("兑换码设置", Navigation.Type.NORMAL, "兑换码管理")
                .build();
    }

    @PostMapping("/settings")
    public ModelAndView settingsPost(@Valid @ModelAttribute(FORM_OBJECT) AdminRedeemSettingsForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改兑换码配置", context -> {
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_TYPE_LIST, trimTypeContent(form.getType()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_ENABLE, String.valueOf(form.isEnable()));
            systemConfigService.update(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_DEFAULT_EXPIRE_DURATION, form.getExpireDuration().toString());
        }).build();
    }

    @GetMapping("/list")
    public ModelAndView redeemList(Pageable pageable) {
        return builder().setTitle("兑换码列表")
                .setTableView(new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.ASC, "status"), redeemKeyService)
                .addNavigation("兑换码列表", Navigation.Type.NORMAL, "兑换码管理")
                .build();
    }

    @GetMapping("/list/{id}")
    public ModelAndView redeemDetails(@PathVariable String id) {
        return redeemKeyService.findOne(id).map(redeemKey -> builder().addDictionaryView(dictionaryViewBuilder -> {
            dictionaryViewBuilder.title("兑换码详情")
                    .dictionary("ID", redeemKey.getId())
                    .dictionary("业务类型", redeemKey.getType())
                    .dictionary("业务数据", redeemKey.getContent())
                    .dictionary("使用状态", redeemKey.getStatus().getValue())
                    .dictionary("创建时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(redeemKey.getDateCreated()))
                    .dictionary("过期时间", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(redeemKey.getDateExpired()));

            userService.findOne(redeemKey.getUserId()).ifPresent(user -> {
                dictionaryViewBuilder.dictionary("使用者ID", user.getId());
                dictionaryViewBuilder.dictionary("使用者", StringUtils.isNotEmpty(user.getNickname()) ? user.getNickname() : user.getUsername());
            });

        }).build()).orElse(error(HttpStatus.NOT_FOUND, "兑换码不存在").build());
    }

    private List<String> getRedeemTypes() {
        List<String> types = Lists.newArrayList();
        String typeContent = systemConfigService.getString(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_TYPE_LIST).orElse(QXCMPSystemConfigConfiguration.SYSTEM_CONFIG_REDEEM_TYPE_LIST_DEFAULT_VALUE);
        types.addAll(Arrays.asList(typeContent.split("\n")));
        return types;
    }

    private String trimTypeContent(String type) {
        return StringUtils.join(Arrays.stream(type.split("\n")).map(String::trim).collect(Collectors.toList()), "\n");
    }
}
