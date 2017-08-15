package com.qxcmp.framework.account.phone;

import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.domain.CodeService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPFrontendController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

import static com.qxcmp.framework.account.AccountService.ACCOUNT_PAGE;

@Controller
@RequestMapping("/account/phone/")
@RequiredArgsConstructor
public class AccountPhoneController extends QXCMPFrontendController {

    private final CodeService codeService;

    /**
     * 邮箱账户注册页面
     *
     * @return 邮箱账户注册页面
     */
    @GetMapping("logon")
    public ModelAndView logon(final AccountPhoneLogonForm form) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("注册功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        return builder(ACCOUNT_PAGE).setFormView(form).build();
    }

    @PostMapping("logon")
    public ModelAndView logon(@Valid @ModelAttribute(FORM_OBJECT) AccountPhoneLogonForm form, BindingResult bindingResult) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("注册功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        if (userService.findById(form.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "Username.exist");
        }

        if (userService.findById(form.getPhone()).isPresent()) {
            bindingResult.rejectValue("phone", "Phone.exist");
        }

        if (!Objects.equals(form.getPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
        }

        validateCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return builder(ACCOUNT_PAGE).setFormView(form).build();
        }

        try {
            userService.create(() -> {
                User user = userService.next();
                user.setPhone(form.getPhone());
                user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                user.setEnabled(true);
                userService.setDefaultPortrait(user);
                return user;
            });

            return builder(ACCOUNT_PAGE).setResult("注册成功", "您现在可以登录了").setResultNavigation("现在去登录", "/login").build();
        } catch (Exception e) {
            return builder(ACCOUNT_PAGE).setResult("注册失败", e.getMessage()).setResultNavigation("现在去登录", "/login").build();
        }
    }

    @GetMapping("reset")
    public ModelAndView reset(final AccountPhoneResetForm form) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        return builder(ACCOUNT_PAGE).setFormView(form).build();
    }

    @PostMapping("reset")
    public ModelAndView reset(@Valid @ModelAttribute(FORM_OBJECT) AccountPhoneResetForm form, BindingResult bindingResult) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_PHONE).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        Optional<User> userOptional = userService.findByPhone(form.getPhone());

        if (!userOptional.isPresent()) {
            bindingResult.rejectValue("phone", "Account.reset.noPhone");
        }

        validateCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return builder(ACCOUNT_PAGE).setFormView(form).build();
        }

        Code code = codeService.nextPasswordCode(userOptional.get().getId());

        return redirect("/account/reset/" + code.getId());
    }
}
