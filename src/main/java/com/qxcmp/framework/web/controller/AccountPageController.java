package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.account.AccountActivateForm;
import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.domain.CodeService;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPFrontendController;
import com.qxcmp.framework.web.form.LoginForm;
import com.qxcmp.framework.web.view.AbstractPage;
import com.qxcmp.framework.web.view.elements.button.AnimatedButton;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.container.Container;
import com.qxcmp.framework.web.view.elements.divider.Divider;
import com.qxcmp.framework.web.view.elements.divider.HorizontalDivider;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.VerticallyDividedGrid;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.IconHeader;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.image.Image;
import com.qxcmp.framework.web.view.elements.list.List;
import com.qxcmp.framework.web.view.elements.list.item.TextItem;
import com.qxcmp.framework.web.view.elements.message.ErrorMessage;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.ColumnCount;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import static com.qxcmp.framework.web.auth.AuthenticationFailureHandler.AUTHENTICATION_ERROR_MESSAGE;

/**
 * 账户登录、注册、重置页面路由
 *
 * @author Aaric
 */
@Controller
@RequiredArgsConstructor
public class AccountPageController extends QXCMPFrontendController {

    protected final AccountService accountService;

    protected final CodeService codeService;

    @GetMapping("/login")
    public ModelAndView loginPage() {

        LoginForm loginForm = new LoginForm();

        return buildPage(segment -> segment
                .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setDividing())
                .addComponent(convertToForm(loginForm).setSubmitButton(new AnimatedButton().setVisibleText("登录").setHiddenIcon(new Icon("sign in")).setAnimatedType(AnimatedButton.AnimatedType.FADE).setSecondary()).setErrorMessage(getLoginErrorMessage()))
                .addComponent(new HorizontalDivider("或"))
                .addComponent(new Container().setAlignment(Alignment.CENTER).addComponent(new Anchor("注册新用户", "/account/logon")).addComponent(new Anchor("忘记密码?", "/account/reset")))).addObject(loginForm)
                .build();
    }

    @GetMapping("/account/logon")
    public ModelAndView logon() {
        if (accountService.getRegisterItems().isEmpty()) {
            return logonClosedPage().build();
        } else if (accountService.getRegisterItems().size() == 1) {
            return redirect(accountService.getRegisterItems().get(0).getRegisterUrl());
        } else {
            List list = new List().setSelection();
            accountService.getRegisterItems().forEach(accountComponent -> list.addItem(new TextItem(accountComponent.getRegisterName()).setUrl(accountComponent.getRegisterUrl())));
            return buildPage(segment -> segment.setAlignment(Alignment.CENTER)
                    .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setSubTitle("请选择注册方式").setDividing().setAlignment(Alignment.LEFT))
                    .addComponent(list)
                    .addComponent(new Divider())
                    .addComponent(new Button("返回登录", "/login").setBasic().setPrimary())
            ).build();
        }
    }

    @GetMapping("/account/reset")
    public ModelAndView reset() {
        if (accountService.getResetItems().isEmpty()) {
            return resetClosedPage().build();
        } else if (accountService.getResetItems().size() == 1) {
            return redirect(accountService.getResetItems().get(0).getResetUrl());
        } else {
            List list = new List().setSelection();
            accountService.getRegisterItems().forEach(accountComponent -> list.addItem(new TextItem(accountComponent.getResetName()).setUrl(accountComponent.getResetUrl())));
            return buildPage(segment -> segment.setAlignment(Alignment.CENTER)
                    .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setSubTitle("请选择密码找回方式").setDividing().setAlignment(Alignment.LEFT))
                    .addComponent(list)
                    .addComponent(new Divider())
                    .addComponent(new Button("返回登录", "/login").setBasic().setPrimary())
            ).build();
        }
    }

    //
//    @GetMapping("reset/{id}")
//    public ModelAndView reset(@PathVariable String id) {
//
//        if (codeService.isInvalidCode(id)) {
//            return builder(ACCOUNT_PAGE).setResult("无效的重置链接", "请确认重置链接是否正确，或者重新找回密码")
//                    .setResultNavigation("返回登录", "/login", "重新找回密码", "/account/reset")
//                    .build();
//        }
//
//        return accountResetForm(codeService.findOne(id).orElse(null), new AccountResetForm());
//    }
//
//    @PostMapping("reset/{id}")
//    public ModelAndView reset(@PathVariable String id, @Valid @ModelAttribute(FORM_OBJECT) AccountResetForm form, BindingResult bindingResult) throws Exception {
//
//        Code code = codeService.findOne(id).orElse(null);
//
//        if (codeService.isInvalidCode(id)) {
//            return builder(ACCOUNT_PAGE).setResult("重置密码失败", "重置链接已经失效")
//                    .setResultNavigation("返回登录", "/login", "重新找回密码", "/account/reset")
//                    .build();
//        }
//
//        if (!StringUtils.equals(form.getPassword(), form.getPasswordConfirm())) {
//            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
//        }
//
//        if (bindingResult.hasErrors()) {
//            return accountResetForm(code, form);
//        }
//
//        userService.update(code.getUserId(), user -> {
//            user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
//            codeService.remove(code);
//        });
//
//        return builder(ACCOUNT_PAGE).setResult("密码重置成功", "请使用新的密码登录").setResultNavigation("现在去登录", "/login").build();
//    }
//
    @GetMapping("/account/activate")
    public ModelAndView activate(final AccountActivateForm form) {
        return buildPage(segment -> segment
                .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setSubTitle("激活账户").setDividing().setAlignment(Alignment.LEFT))
                .addComponent(convertToForm(form))
        ).addObject(form)
                .build();
    }

    @PostMapping("/account/activate")
    public ModelAndView activate(@Valid final AccountActivateForm form, BindingResult bindingResult) {

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

        verifyCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return buildPage(segment -> segment
                    .addComponent(new PageHeader(HeaderType.H2, qxcmpConfiguration.getTitle()).setImage(new Image(qxcmpConfiguration.getLogo())).setSubTitle("激活账户").setDividing().setAlignment(Alignment.LEFT))
                    .addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
            ).addObject(form)
                    .build();
        }

        try {
            accountService.sendActivateEmail(userOptional.get());

            return buildPage(segment -> segment.setAlignment(Alignment.CENTER)
                    .addComponent(new PageHeader(HeaderType.H2, "发送激活邮件成功").setSubTitle("激活邮件已经发送到您的邮件，请前往激活。如果您未收到激活邮件，请检查是否被黑名单过滤，或者再次重新发送激活邮件"))
                    .addComponent(new Divider())
                    .addComponent(new Button("立即登录", "/login").setBasic())
            ).build();
        } catch (Exception e) {
            return page().addComponent(new Overview("发送激活邮件失败", e.getMessage()).addLink("返回登录", "/login")).build();
        }
    }

//    @GetMapping("activate/{id}")
//    public ModelAndView activate(@PathVariable String id) {
//        try {
//            if (codeService.isInvalidCode(id)) {
//                return builder(ACCOUNT_PAGE).setResult("账户激活失败", "无效的激活码", "请确认激活码是否正确，或者重新发送激活码")
//                        .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
//                        .build();
//            }
//
//            Optional<Code> codeOptional = codeService.findOne(id);
//
//            Code code = codeOptional.get();
//
//            if (!code.getType().equals(Code.Type.ACTIVATE)) {
//                return builder(ACCOUNT_PAGE).setResult("账户激活失败", "无效的激活码", "请确认激活码是否正确，或者重新发送激活码")
//                        .setResultNavigation("返回登录", "/login", "重新激活账户", "/account/activate")
//                        .build();
//            }
//
//            userService.update(code.getUserId(), user -> {
//                user.setEnabled(true);
//                codeService.remove(code);
//            });
//
//            return builder(ACCOUNT_PAGE).setResult("账户激活成功", "现在可以登录您的账户了").setResultNavigation("现在去登录", "/login").build();
//        } catch (Exception e) {
//            return builder(ACCOUNT_PAGE).setResult("账户激活失败", e.getMessage()).setResultNavigation("重新发送邮件", "/account/email/reset").build();
//        }
//    }
//
//    private ModelAndView accountResetForm(Code code, AccountResetForm accountResetForm) {
//        String username = userService.findOne(code.getUserId()).map(user -> {
//            if (StringUtils.isNotBlank(user.getUsername())) {
//                return user.getUsername();
//            }
//
//            if (StringUtils.isNotBlank(user.getEmail())) {
//                return user.getEmail();
//            }
//
//            if (StringUtils.isNotBlank(user.getPhone())) {
//                return user.getPhone();
//            }
//
//            return null;
//        }).orElse("Null User");
//
//        ModelAndView modelAndView = builder(ACCOUNT_PAGE).setFormView(accountResetForm).build();
//        FormViewEntity formViewEntity = (FormViewEntity) modelAndView.getModel().get("formViewEntity");
//        formViewEntity.setCaption(String.format("为账户%s找回密码", username));
//        return modelAndView;
//    }

    /**
     * 账户页面基本结构
     *
     * @param consumer
     *
     * @return
     */
    protected AbstractPage buildPage(Consumer<Segment> consumer) {
        Segment segment = new Segment();
        consumer.accept(segment);
        return page().addComponent(new VerticallyDividedGrid().setVerticallyPadded().setTextContainer().setColumnCount(ColumnCount.ONE).addItem(new Col().addComponent(segment)));
    }

    protected AbstractPage logonClosedPage() {
        return buildPage(segment -> segment.setAlignment(Alignment.CENTER)
                .addComponent(new IconHeader("注册功能已经关闭", new Icon("warning circle")).setSubTitle("请在注册功能开放时进行注册"))
                .addComponent(new Divider())
                .addComponent(new Button("返回登录", "/login").setBasic())
        );
    }

    protected AbstractPage resetClosedPage() {
        return buildPage(segment -> segment.setAlignment(Alignment.CENTER)
                .addComponent(new IconHeader("密码找回功能已经关闭", new Icon("warning circle")).setSubTitle("请与平台管理员联系"))
                .addComponent(new Divider())
                .addComponent(new Button("返回登录", "/login").setBasic())
        );
    }

    /**
     * 获取登录错误消息
     *
     * @return
     */
    private ErrorMessage getLoginErrorMessage() {

        if (Objects.nonNull(request.getSession().getAttribute(AUTHENTICATION_ERROR_MESSAGE))) {
            return new ErrorMessage("登录失败", applicationContext.getMessage(request.getSession().getAttribute(AUTHENTICATION_ERROR_MESSAGE).toString(), null, null));
        }

        return null;
    }
}
