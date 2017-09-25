package com.qxcmp.framework.security.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.security.PrivilegeService;
import com.qxcmp.framework.security.RoleService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.elements.segment.Segments;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(QXCMP_BACKEND_URL + "/security")
@RequiredArgsConstructor
public class AdminSecurityPageController extends QXCMPBackendController {

    private final PrivilegeService privilegeService;

    private final RoleService roleService;

    @GetMapping("")
    public ModelAndView messagePage() {
        return page().addComponent(new Segments())
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "安全配置")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/privilege")
    public ModelAndView privilegePage(Pageable pageable) {
        return page().addComponent(convertToTable(pageable, privilegeService))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "安全配置")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/authentication")
    public ModelAndView authenticationPage(final AdminSecurityAuthenticationForm form) {

        form.setCaptchaThreshold(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD).orElse(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD_DEFAULT_VALUE));
        form.setCaptchaLength(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH).orElse(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH_DEFAULT_VALUE));
        form.setLockAccount(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DEFAULT_VALUE));
        form.setLockThreshold(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD_DEFAULT_VALUE));
        form.setUnlockDuration(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION_DEFAULT_VALUE));
        form.setExpireAccount(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DEFAULT_VALUE));
        form.setExpireAccountDuration(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION_DEFAULT_VALUE));
        form.setExpireCredential(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE).orElse(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DEFAULT_VALUE));
        form.setExpireCredentialDuration(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION).orElse(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION_DEFAULT_VALUE));
        form.setUniqueCredential(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE).orElse(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE_DEFAULT_VALUE));

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "安全配置", QXCMP_BACKEND_URL + "/security", "认证配置")
                .setVerticalMenu(getVerticalMenu("认证配置"))
                .build();
    }

    @PostMapping("/authentication")
    public ModelAndView authenticationPage(@Valid final AdminSecurityAuthenticationForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "安全配置", QXCMP_BACKEND_URL + "/security", "认证配置")
                    .setVerticalMenu(getVerticalMenu("认证配置"))
                    .build();
        }

        return submitForm(form, context -> {
            try {
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD, String.valueOf(form.getCaptchaThreshold()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH, String.valueOf(form.getCaptchaLength()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK, String.valueOf(form.isLockAccount()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD, String.valueOf(form.getLockThreshold()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION, String.valueOf(form.getUnlockDuration()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE, String.valueOf(form.isExpireAccount()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION, String.valueOf(form.getExpireAccountDuration()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE, String.valueOf(form.isExpireCredential()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION, String.valueOf(form.getExpireCredentialDuration()));
                systemConfigService.update(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE, String.valueOf(form.isUniqueCredential()));
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "角色管理", QXCMP_BACKEND_URL + "/security/role", "权限管理", QXCMP_BACKEND_URL + "/security/privilege", "认证配置", QXCMP_BACKEND_URL + "/security/authentication");
    }
}
