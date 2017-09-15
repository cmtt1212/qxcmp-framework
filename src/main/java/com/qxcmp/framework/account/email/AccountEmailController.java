package com.qxcmp.framework.account.email;

import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.domain.CodeService;
import com.qxcmp.framework.web.controller.AccountPageController;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.support.Alignment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account/email/")
public class AccountEmailController extends AccountPageController {

    public AccountEmailController(AccountService accountService, CodeService codeService) {
        super(accountService, codeService);
    }

    @GetMapping("logon")
    public ModelAndView logon(final AccountEmailLogonForm form) {

        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false)) {
            return logonClosedPage().build();
        }

        return buildPage(segment -> segment
                .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setSubTitle("邮箱注册").setDividing().setAlignment(Alignment.LEFT))
                .addComponent(convertToForm(form))
        ).addObject(form)
                .build();
    }
//
//    @PostMapping("logon")
//    public ModelAndView logonEmailPost(@Valid @ModelAttribute(FORM_OBJECT) AccountEmailLogonForm form, BindingResult bindingResult) {
//
//        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false)) {
//            return builder(ACCOUNT_PAGE).setResult("注册功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
//        }
//
//        if (userService.findById(form.getUsername()).isPresent()) {
//            bindingResult.rejectValue("username", "Username.exist");
//        }
//
//        if (userService.findById(form.getEmail()).isPresent()) {
//            bindingResult.rejectValue("email", "Email.exist");
//        }
//
//        if (!Objects.equals(form.getPassword(), form.getPasswordConfirm())) {
//            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
//        }
//
//        validateCaptcha(form.getCaptcha(), bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return builder(ACCOUNT_PAGE).setFormView(form).build();
//        }
//
//        try {
//            userService.create(() -> {
//                User user = userService.next();
//                user.setUsername(form.getUsername());
//                user.setEmail(form.getEmail());
//                user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
//                user.setAccountNonExpired(true);
//                user.setAccountNonLocked(true);
//                user.setCredentialsNonExpired(true);
//                user.setEnabled(false);
//                userService.setDefaultPortrait(user);
//                return user;
//            }).ifPresent(accountService::sendActivateEmail);
//
//            return builder(ACCOUNT_PAGE)
//                    .setResult("注册成功", "激活邮件已经发送到您的邮件，请前往激活", "如果您未收到激活邮件，请检查是否被黑名单过滤，或者再次重新发送激活邮件")
//                    .setResultNavigation("现在去登录", "/login", "重新激活账户", "/account/activate")
//                    .build();
//        } catch (Exception e) {
//            return builder(ACCOUNT_PAGE).setResult("注册失败", e.getMessage())
//                    .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
//                    .build();
//        }
//    }
//
//    @GetMapping("reset")
//    public ModelAndView reset(final AccountEmailResetForm form) {
//
//        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false)) {
//            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
//        }
//
//        return builder(ACCOUNT_PAGE).setFormView(form).build();
//    }
//
//    @PostMapping("reset")
//    public ModelAndView resetEmailPost(@Valid @ModelAttribute(FORM_OBJECT) AccountEmailResetForm form, BindingResult bindingResult) {
//
//        if (!systemConfigService.getBoolean(QXCMPConfiguration.SYSTEM_CONFIG_ACCOUNT_ENABLE_EMAIL).orElse(false)) {
//            return builder(ACCOUNT_PAGE).setResult("密码找回功能已经关闭", "请与平台管理员联系").setResultNavigation("返回登录", "/login").build();
//        }
//
//        Optional<User> userOptional = userService.findByEmail(form.getEmail());
//
//        if (!userOptional.isPresent()) {
//            bindingResult.rejectValue("email", "Account.reset.noEmail");
//        }
//
//        validateCaptcha(form.getCaptcha(), bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return builder(ACCOUNT_PAGE).setFormView(form).build();
//        }
//
//        try {
//            accountService.sendResetEmail(userOptional.get());
//            return builder(ACCOUNT_PAGE).setResult("密码重置邮件发送成功", "请前往您的邮箱点击重置链接重置密码").setResultNavigation("返回登录", "/login").build();
//        } catch (Exception e) {
//            return builder(ACCOUNT_PAGE).setResult("密码重置邮件发送失败", e.getMessage()).setResultNavigation("返回登录", "/login").build();
//        }
//    }
}
