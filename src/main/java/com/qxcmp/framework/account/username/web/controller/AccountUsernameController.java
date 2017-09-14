package com.qxcmp.framework.account.username.web.controller;

import com.qxcmp.framework.account.username.SecurityQuestion;
import com.qxcmp.framework.account.username.SecurityQuestionService;
import com.qxcmp.framework.account.username.web.form.AccountUsernameLogonForm;
import com.qxcmp.framework.account.username.web.form.AccountUsernameResetForm;
import com.qxcmp.framework.account.username.web.form.AccountUsernameResetQuestionForm;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.domain.CodeService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPFrontendController2;
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
@RequestMapping("/account/username/")
@RequiredArgsConstructor
public class AccountUsernameController extends QXCMPFrontendController2 {

    private final SecurityQuestionService securityQuestionService;

    private final CodeService codeService;

    @GetMapping("logon")
    public ModelAndView logon(final AccountUsernameLogonForm form) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("注册功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        return builder(ACCOUNT_PAGE).setFormView(form).build();
    }

    @PostMapping("logon")
    public ModelAndView logonEmailPost(@Valid @ModelAttribute(FORM_OBJECT) AccountUsernameLogonForm form, BindingResult bindingResult) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("注册功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        if (userService.findById(form.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "Username.exist");
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
                user.setUsername(form.getUsername());
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
    public ModelAndView reset(final AccountUsernameResetForm form) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        return builder(ACCOUNT_PAGE).setFormView(form).build();
    }

    @PostMapping("reset")
    public ModelAndView reset(@Valid @ModelAttribute(FORM_OBJECT) AccountUsernameResetForm form, BindingResult bindingResult) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        Optional<User> userOptional = userService.findById(form.getUsername());

        if (!userOptional.isPresent()) {
            bindingResult.rejectValue("username", "Account.reset.noUsername");
        } else {
            if (!securityQuestionService.findByUserId(userOptional.get().getId()).isPresent()) {
                bindingResult.rejectValue("username", "Account.reset.noQuestion");
            }
        }

        validateCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return builder(ACCOUNT_PAGE).setFormView(form).build();
        }

        Optional<SecurityQuestion> securityQuestionOptional = securityQuestionService.findByUserId(userOptional.get().getId());

        final AccountUsernameResetQuestionForm accountUsernameResetQuestionForm = new AccountUsernameResetQuestionForm();

        accountUsernameResetQuestionForm.setQuestion1(securityQuestionOptional.get().getQuestion1());
        accountUsernameResetQuestionForm.setQuestion2(securityQuestionOptional.get().getQuestion2());
        accountUsernameResetQuestionForm.setQuestion3(securityQuestionOptional.get().getQuestion3());
        accountUsernameResetQuestionForm.setUserId(securityQuestionOptional.get().getUserId());

        return builder(ACCOUNT_PAGE).setTitle("找回密码")
                .setFormView(accountUsernameResetQuestionForm)
                .build();
    }

    @PostMapping("reset/question")
    public ModelAndView securityQuestion(@ModelAttribute(FORM_OBJECT) AccountUsernameResetQuestionForm form, BindingResult bindingResult) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(false)) {
            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
        }

        return securityQuestionService.findByUserId(form.getUserId()).map(securityQuestion -> {
            if (validateQuestion(form, securityQuestion)) {
                Code code = codeService.nextPasswordCode(securityQuestion.getUserId());
                return redirect("/account/reset/" + code.getId());
            } else {
                return builder(ACCOUNT_PAGE).setTitle("找回密码")
                        .setResult("密保问题错误")
                        .setResultNavigation("重新找回密码", "/account/username/reset")
                        .build();
            }
        }).orElse(builder(ACCOUNT_PAGE).setTitle("找回密码")
                .setResult("用户为设置密保问题")
                .build());
    }

    private boolean validateQuestion(AccountUsernameResetQuestionForm form, SecurityQuestion securityQuestion) {

        int count = 0;

        if (form.getAnswer1().equals(securityQuestion.getAnswer1())) {
            count++;
        }

        if (form.getAnswer2().equals(securityQuestion.getAnswer2())) {
            count++;
        }

        if (form.getAnswer3().equals(securityQuestion.getAnswer3())) {
            count++;
        }

        return count >= 2;
    }
}
