package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.security.Privilege;
import com.qxcmp.framework.security.PrivilegeService;
import com.qxcmp.framework.security.Role;
import com.qxcmp.framework.security.RoleService;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.AdminSecurityAuthenticationForm;
import com.qxcmp.framework.web.form.AdminSecurityRoleForm;
import com.qxcmp.framework.web.form.AdminSecurityUserForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;

/**
 * 安全模块页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/security")
@RequiredArgsConstructor
public class SecurityModuleController extends QXCMPBackendController2 {

    private final PrivilegeService privilegeService;

    private final RoleService roleService;

    @GetMapping
    public ModelAndView security() {
        return builder().setTitle("安全设置")
                .addDictionaryView(dictionaryViewBuilder -> dictionaryViewBuilder
                        .title("平台安全配置")
                        .dictionary("用户总数", String.valueOf(userService.count()))
                        .dictionary("权限总数", String.valueOf(privilegeService.count()))
                        .dictionary("角色总数", String.valueOf(roleService.count())))
                .addNavigation(Navigation.Type.NORMAL, "安全设置")
                .build();
    }

    @GetMapping("/user")
    public ModelAndView roleUser(Pageable pageable) {
        return builder().setTitle("用户状态管理")
                .setTableView("user", pageable, userService)
                .addNavigation("用户状态管理", Navigation.Type.NORMAL, "安全设置")
                .build();
    }

    @GetMapping("/user/{username}/edit")
    public ModelAndView roleUserEdit(@PathVariable String username) {
        return userService.findByUsername(username).map(user -> {
            AdminSecurityUserForm securityUserForm = new AdminSecurityUserForm();
            securityUserForm.setId(user.getId());
            securityUserForm.setUsername(user.getUsername());
            securityUserForm.setRoles(user.getRoles());
            securityUserForm.setEnabled(user.isEnabled());
            securityUserForm.setCredentialsNonExpired(user.isCredentialsNonExpired());
            securityUserForm.setAccountNonExpired(user.isAccountNonExpired());
            securityUserForm.setAccountNonLocked(user.isAccountNonLocked());
            return builder().setTitle("修改用户状态")
                    .setFormView(securityUserForm, roleService.findAll())
                    .addNavigation("用户状态管理", Navigation.Type.NORMAL, "安全设置")
                    .build();
        }).orElseGet(() -> error(HttpStatus.NOT_FOUND, "用户不存在").build());
    }

    @PostMapping("/user")
    public ModelAndView roleUserEdit(@Valid @ModelAttribute("object") AdminSecurityUserForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改用户角色", context -> {
            userService.update(form.getId(), user -> {
                user.setRoles(form.getRoles());
                user.setAccountNonLocked(form.isAccountNonLocked());
                user.setAccountNonExpired(form.isAccountNonExpired());
                user.setCredentialsNonExpired(form.isCredentialsNonExpired());
                user.setEnabled(form.isEnabled());
            }).orElseThrow(() -> new ActionException("User not exist"));
        }).build();
    }

    @GetMapping("/privilege")
    public ModelAndView privilegeTable(Pageable pageable) {
        return builder().setTitle("平台权限管理")
                .setResult("权限列表", "您可以启动/禁用某个权限")
                .addDivider()
                .addListView(pageable, privilegeService, request)
                .addNavigation("平台权限管理", Navigation.Type.NORMAL, "安全设置")
                .build();
    }

    @GetMapping("/privilege/{id}/edit")
    public ModelAndView privilegeEdit(@PathVariable String id) {
        try {
            Long pId = Long.parseLong(id);

            Optional<Privilege> privilegeOptional = privilegeService.findOne(pId);

            return privilegeOptional.map(privilege -> builder().setTitle("平台权限管理")
                    .setFormView(privilege)
                    .addNavigation("平台权限管理", Navigation.Type.NORMAL, "安全设置")
                    .build())
                    .orElseGet(() -> error(HttpStatus.NOT_FOUND, "").build());

        } catch (NumberFormatException e) {
            return error(HttpStatus.NOT_FOUND, "").build();
        }
    }

    @PostMapping("/privilege")
    public ModelAndView privilegeEdit(@Valid Privilege privilege, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(privilege).build();
        }

        return action("启用、禁用权限", context -> {
            if (privilege.isDisabled()) {
                privilegeService.disable(privilege.getId().toString());
            } else {
                privilegeService.enable(privilege.getId().toString());
            }
        }).build();
    }

    @GetMapping("/role")
    public ModelAndView roleTable(Pageable pageable) {
        return builder().setTitle("平台角色管理")
                .setTableView(pageable, roleService)
                .addNavigation("平台角色管理", Navigation.Type.NORMAL, "安全设置")
                .build();
    }

    @PostMapping("/role")
    public ModelAndView roleCreateOrUpdate(@Valid @ModelAttribute("object") AdminSecurityRoleForm securityRoleForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(securityRoleForm, privilegeService.findAll()).build();
        }

        if (securityRoleForm.getId() == null) {
            return action("新建角色", context -> {
                try {
                    roleService.create(() -> {
                        Role role = roleService.next();
                        role.setName(securityRoleForm.getName());
                        role.setDescription(securityRoleForm.getDescription());
                        role.setPrivileges(new HashSet<>(securityRoleForm.getPrivilegeList()));
                        return role;
                    });
                } catch (Exception e) {
                    throw new ActionException(e.getMessage());
                }
            }).build();
        } else {
            return action("编辑角色", context -> {
                roleService.update(securityRoleForm.getId(), role -> {
                    role.setName(securityRoleForm.getName());
                    role.setDescription(securityRoleForm.getDescription());
                    role.setPrivileges(new HashSet<>(securityRoleForm.getPrivilegeList()));
                }).orElseThrow(() -> new ActionException("User not exist"));
            }).build();
        }
    }

    @GetMapping("/role/new")
    public ModelAndView roleNew(final AdminSecurityRoleForm securityRoleForm) {
        return builder().setTitle("新建角色管理")
                .setFormView(securityRoleForm, privilegeService.findAll())
                .addNavigation("平台角色管理", Navigation.Type.NORMAL, "安全设置")
                .build();
    }

    @GetMapping("/role/{id}/edit")
    public ModelAndView roleEdit(@PathVariable String id) {
        try {
            Long rId = Long.parseLong(id);
            return roleService.findOne(rId).map(role -> {
                AdminSecurityRoleForm securityRoleForm = new AdminSecurityRoleForm();
                securityRoleForm.setId(role.getId());
                securityRoleForm.setName(role.getName());
                securityRoleForm.setDescription(role.getDescription());
                securityRoleForm.setPrivilegeList(new ArrayList<>(role.getPrivileges()));
                return builder().setTitle("修改平台角色")
                        .setFormView(securityRoleForm, privilegeService.findAll())
                        .addNavigation("平台角色管理", Navigation.Type.NORMAL, "安全设置")
                        .build();
            }).orElseGet(() -> error(HttpStatus.NOT_FOUND, "").build());
        } catch (NumberFormatException e) {
            return error(HttpStatus.NOT_FOUND, "").build();
        }
    }

    @PostMapping("/role/{id}/delete")
    public ModelAndView roleDelete(@PathVariable String id) {
        try {
            Long rId = Long.parseLong(id);
            return action("删除角色", context -> roleService.remove(rId)).build();
        } catch (NumberFormatException e) {
            return error(HttpStatus.NOT_FOUND, "").build();
        }
    }

    @GetMapping("/authentication")
    public ModelAndView authenticationEdit(AdminSecurityAuthenticationForm form) {
        form.setCaptchaThreshold(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD).orElse(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_THRESHOLD_DEFAULT_VALUE));
        form.setCaptchaLength(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH).orElse(SYSTEM_CONFIG_AUTHENTICATION_CAPTCHA_LENGTH_DEFAULT_VALUE));
        form.setLockThreshold(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_THRESHOLD_DEFAULT_VALUE));
        form.setLockAccount(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DEFAULT_VALUE));
        form.setUnlockDuration(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_LOCK_DURATION_DEFAULT_VALUE));
        form.setExpireAccount(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DEFAULT_VALUE));
        form.setExpireAccountDuration(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION).orElse(SYSTEM_CONFIG_AUTHENTICATION_ACCOUNT_EXPIRE_DURATION_DEFAULT_VALUE));
        form.setExpireCredential(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE).orElse(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DEFAULT_VALUE));
        form.setExpireCredentialDuration(systemConfigService.getInteger(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION).orElse(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_EXPIRE_DURATION_DEFAULT_VALUE));
        form.setUniqueCredential(systemConfigService.getBoolean(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE).orElse(SYSTEM_CONFIG_AUTHENTICATION_CREDENTIAL_UNIQUE_DEFAULT_VALUE));
        return builder().setTitle("平台认证配置")
                .setFormView(form)
                .addNavigation("平台认证配置", Navigation.Type.NORMAL, "安全设置")
                .build();
    }

    @PostMapping("/authentication")
    public ModelAndView authenticationEdit(@Valid @ModelAttribute("object") AdminSecurityAuthenticationForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改平台认证方式", context -> {
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
                throw new ActionException(e.getMessage());
            }
        }).build();
    }
}
