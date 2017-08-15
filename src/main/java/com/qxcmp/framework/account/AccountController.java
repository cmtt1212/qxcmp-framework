package com.qxcmp.framework.account;

import com.qxcmp.framework.domain.Code;
import com.qxcmp.framework.domain.CodeService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.view.form.FormViewEntity;
import com.qxcmp.framework.view.list.ListViewItem;
import com.qxcmp.framework.web.QXCMPFrontendController;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

import static com.qxcmp.framework.account.AccountService.ACCOUNT_PAGE;

/**
 * 账户模块页面路由 <li>账户注册页面的处理</li> <li>账户重置页面的处理</li> <li>账户激活页面的处理</li>
 *
 * @author aaric
 */
@Controller
@RequestMapping("/account/")
@RequiredArgsConstructor
public class AccountController extends QXCMPFrontendController {

    private final AccountService accountService;

    private final CodeService codeService;

    @GetMapping("logon")
    public ModelAndView logon() {
        if (accountService.getRegisterItems().isEmpty()) {
            return builder(ACCOUNT_PAGE)
                    .setResult("注册功能已经关闭", "请与平台管理员联系")
                    .setResultNavigation("返回登录", "/login")
                    .build();
        } else if (accountService.getRegisterItems().size() == 1) {
            return redirect(accountService.getRegisterItems().get(0).getRegisterUrl());
        } else {
            return builder(ACCOUNT_PAGE)
                    .setResult("请选择注册方式", "")
                    .addListView(listViewBuilder -> accountService.getRegisterItems().forEach(accountComponent -> listViewBuilder.item(ListViewItem.builder().title(accountComponent.getRegisterName()).link(accountComponent.getRegisterUrl()).build())))
                    .setResultNavigation("返回登录", "/login")
                    .build();
        }
    }

    @GetMapping("reset")
    public ModelAndView reset() {
        if (accountService.getResetItems().isEmpty()) {
            return builder(ACCOUNT_PAGE)
                    .setResult("注册密码找回功能已经关闭", "请与平台管理员联系")
                    .setResultNavigation("返回登录", "/login")
                    .build();
        } else if (accountService.getResetItems().size() == 1) {
            return redirect(accountService.getResetItems().get(0).getResetUrl());
        } else {
            return builder(ACCOUNT_PAGE)
                    .setResult("请选择密码找回方式", "")
                    .addListView(listViewBuilder -> accountService.getResetItems().forEach(accountComponent -> listViewBuilder.item(ListViewItem.builder().title(accountComponent.getResetName()).link(accountComponent.getResetUrl()).build())))
                    .setResultNavigation("返回登录", "/login")
                    .build();
        }
    }

    @GetMapping("reset/{id}")
    public ModelAndView reset(@PathVariable String id) {

        if (codeService.isInvalidCode(id)) {
            return builder(ACCOUNT_PAGE).setResult("无效的重置链接", "请确认重置链接是否正确，或者重新找回密码")
                    .setResultNavigation("返回登录", "/login", "重新找回密码", "/account/reset")
                    .build();
        }

        return accountResetForm(codeService.findOne(id).orElse(null), new AccountResetForm());
    }

    @PostMapping("reset/{id}")
    public ModelAndView reset(@PathVariable String id, @Valid @ModelAttribute(FORM_OBJECT) AccountResetForm form, BindingResult bindingResult) throws Exception {

        Code code = codeService.findOne(id).orElse(null);

        if (codeService.isInvalidCode(id)) {
            return builder(ACCOUNT_PAGE).setResult("重置密码失败", "重置链接已经失效")
                    .setResultNavigation("返回登录", "/login", "重新找回密码", "/account/reset")
                    .build();
        }

        if (!StringUtils.equals(form.getPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {
            return accountResetForm(code, form);
        }

        userService.update(code.getUserId(), user -> {
            user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
            codeService.remove(code);
        });

        return builder(ACCOUNT_PAGE).setResult("密码重置成功", "请使用新的密码登录").setResultNavigation("现在去登录", "/login").build();
    }

    @GetMapping("activate")
    public ModelAndView activate(final AccountActivateForm form) {
        return builder(ACCOUNT_PAGE).setFormView(form).build();
    }

    @PostMapping("activate")
    public ModelAndView activate(@Valid @ModelAttribute(FORM_OBJECT) AccountActivateForm form, BindingResult bindingResult) {

        Optional<User> userOptional = userService.findByUsername(form.getUsername());

        if (!userOptional.isPresent()) {
            bindingResult.rejectValue("username", "Account.activate.notExist");
        } else {
            if (StringUtils.isBlank(userOptional.get().getEmail())) {
                bindingResult.rejectValue("username", "Account.activate.noEmail");
            }

            if (userOptional.get().isEnabled()) {
                bindingResult.rejectValue("username", "Account.activate.activated");
            }
        }

        validateCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return builder(ACCOUNT_PAGE).setFormView(form).build();
        }

        try {
            accountService.sendActivateEmail(userOptional.get());
            return builder(ACCOUNT_PAGE).setResult("发送激活邮件成功", "激活邮件已经发送到您的邮件，请前往激活", "如果您未收到激活邮件，请检查是否被黑名单过滤，或者再次重新发送激活邮件")
                    .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
                    .build();
        } catch (Exception e) {
            return builder(ACCOUNT_PAGE).setResult("发送激活邮件失败", e.getMessage())
                    .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
                    .build();
        }
    }

    @GetMapping("activate/{id}")
    public ModelAndView activate(@PathVariable String id) {
        try {
            if (codeService.isInvalidCode(id)) {
                return builder(ACCOUNT_PAGE).setResult("账户激活失败", "无效的激活码", "请确认激活码是否正确，或者重新发送激活码")
                        .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
                        .build();
            }

            Optional<Code> codeOptional = codeService.findOne(id);

            Code code = codeOptional.get();

            if (!code.getType().equals(Code.Type.ACTIVATE)) {
                return builder(ACCOUNT_PAGE).setResult("账户激活失败", "无效的激活码", "请确认激活码是否正确，或者重新发送激活码")
                        .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
                        .build();
            }

            userService.update(code.getUserId(), user -> {
                user.setEnabled(true);
                codeService.remove(code);
            });

            return builder(ACCOUNT_PAGE).setResult("账户激活成功", "现在可以登录您的账户了").setResultNavigation("现在去登录", "/login").build();
        } catch (Exception e) {
            return builder(ACCOUNT_PAGE).setResult("账户激活失败", e.getMessage()).setResultNavigation("重新发送邮件", "/account/email/reset").build();
        }
    }

    private ModelAndView accountResetForm(Code code, AccountResetForm accountResetForm) {
        String username = userService.findOne(code.getUserId()).map(user -> {
            if (StringUtils.isNotBlank(user.getUsername())) {
                return user.getUsername();
            }

            if (StringUtils.isNotBlank(user.getEmail())) {
                return user.getEmail();
            }

            if (StringUtils.isNotBlank(user.getPhone())) {
                return user.getPhone();
            }

            return null;
        }).orElse("Null User");

        ModelAndView modelAndView = builder(ACCOUNT_PAGE).setFormView(accountResetForm).build();
        FormViewEntity formViewEntity = (FormViewEntity) modelAndView.getModel().get("formViewEntity");
        formViewEntity.setCaption(String.format("为账户%s找回密码", username));
        return modelAndView;
    }

}
