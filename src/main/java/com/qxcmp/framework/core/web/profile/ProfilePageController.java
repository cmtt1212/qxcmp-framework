package com.qxcmp.framework.core.web.profile;

import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.account.username.AccountSecurityQuestion;
import com.qxcmp.framework.account.username.AccountSecurityQuestionService;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.button.Button;
import com.qxcmp.framework.web.view.elements.grid.Col;
import com.qxcmp.framework.web.view.elements.grid.Grid;
import com.qxcmp.framework.web.view.elements.grid.Row;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.table.*;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;
import static com.qxcmp.framework.core.web.profile.ProfilePageHelper.*;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfilePageController extends QXCMPController {

    private final AccountSecurityQuestionService securityQuestionService;
    private final AccountService accountService;

    @GetMapping("/info")
    public ModelAndView infoPage(final ProfileInfoForm form) {
        User user = userService.currentUser();
        form.setPortrait(user.getPortrait());
        form.setNickname(user.getNickname());
        form.setPersonalizedSignature(user.getPersonalizedSignature());

        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form)))))).build();
    }

    @PostMapping("/info")
    public ModelAndView infoPage(@Valid final AdminProfileInfoForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))).build();
        }

        return submitForm(form, context -> {
            try {
                User user = userService.currentUser();
                userService.update(user.getId(), u -> {
                    u.setPortrait(form.getPortrait());
                    u.setNickname(form.getNickname());
                    u.setPersonalizedSignature(form.getPersonalizedSignature());
                }).ifPresent(u -> refreshUser());
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/info"));
    }

    @GetMapping("/security")
    public ModelAndView securityPage() {
        User user = userService.currentUser();

        boolean hasSecurityQuestion = securityQuestionService.findByUserId(user.getId()).map(accountSecurityQuestion -> StringUtils.isNotBlank(accountSecurityQuestion.getQuestion1()) && StringUtils.isNotBlank(accountSecurityQuestion.getQuestion2()) && StringUtils.isNotBlank(accountSecurityQuestion.getQuestion3())).orElse(false);

        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment()
                .addComponent(new PageHeader(HeaderType.H2, "安全设置").setDividing())
                .addComponent(new Table().setBasic().setHeader((AbstractTableHeader) new TableHeader()
                        .addRow(new TableRow().addCell(new TableHead("登录密码").setAlignment(Alignment.CENTER)).addCell(new TableData("安全性高的密码可以使帐号更安全")).addCell(new TableData(StringUtils.isNotBlank(user.getPassword()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Button("修改", "/profile/security/password").setBasic()).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("支付密码").setAlignment(Alignment.CENTER)).addCell(new TableData("支付密码可以提高在支付场合的安全性")).addCell(new TableData(StringUtils.isNotBlank(user.getPayPassword()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Button(StringUtils.isNotBlank(user.getPayPassword()) ? "修改" : "设置", (StringUtils.isNotBlank(user.getPayPassword()) ? "/profile/security/pay/password/edit" : "/profile/security/pay/password")).setBasic()).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("手机绑定").setAlignment(Alignment.CENTER)).addCell(new TableData("手机号可以直接用于登录、找回密码等")).addCell(new TableData(StringUtils.isNotBlank(user.getPhone()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Button("绑定", "/profile/security/phone").setBasic()).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("邮箱绑定").setAlignment(Alignment.CENTER)).addCell(new TableData("邮箱可以直接用于登录、找回密码等")).addCell(new TableData(StringUtils.isNotBlank(user.getEmail()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Button("绑定", "/profile/security/email").setBasic()).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("密保问题").setAlignment(Alignment.CENTER)).addCell(new TableData("建议设置三个容易记住，且最不容易被他人获取的问题及答案，更有效保障您的密码安全")).addCell(new TableData(hasSecurityQuestion ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Button("设置", "/profile/security/question").setBasic()).setAlignment(Alignment.CENTER)))
                )))))).build();
    }


    @GetMapping("/security/password")
    public ModelAndView securityPasswordPage(final ProfileSecurityPasswordForm form) {
        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form)))))).build();
    }

    @PostMapping("/security/password")
    public ModelAndView securityPasswordPage(@Valid final ProfileSecurityPasswordForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        if (!new BCryptPasswordEncoder().matches(form.getOldPassword(), user.getPassword())) {
            bindingResult.rejectValue("oldPassword", "BadCredential");
        }

        if (!StringUtils.equals(form.getNewPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))).build();
        }

        return submitForm(form, context -> {
            try {
                userService.update(user.getId(), u -> u.setPassword(new BCryptPasswordEncoder().encode(form.getNewPassword())));
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/security"));
    }

    @GetMapping("/security/pay/password")
    public ModelAndView securityPayPasswordPage(final ProfileSecurityPayPasswordForm form) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        checkState(StringUtils.isBlank(user.getPayPassword()), "已经设置了支付密码");

        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form)))))).build();
    }

    @PostMapping("/security/pay/password")
    public ModelAndView securityPayPasswordPage(@Valid final ProfileSecurityPayPasswordForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        if (!StringUtils.equals(form.getPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {

            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))).build();
        }

        return submitForm(form, context -> {
            try {
                userService.update(user.getId(), u -> {
                    u.setPayPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
                    u.getPasswordHistory().add(u.getPayPassword());
                }).ifPresent(u -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities())));

            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/security"));
    }

    @GetMapping("/security/pay/password/edit")
    public ModelAndView securityPayPasswordEditPage(final ProfileSecurityPayPasswordEditForm form) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        checkState(StringUtils.isNotBlank(user.getPayPassword()), "尚未设置支付密码");

        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form)))))).build();
    }

    @PostMapping("/security/pay/password/edit")
    public ModelAndView securityPayPasswordEditPage(@Valid final ProfileSecurityPayPasswordEditForm form, BindingResult bindingResult) {

        User user = currentUser().orElseThrow(RuntimeException::new);

        if (!new BCryptPasswordEncoder().matches(form.getOldPassword(), user.getPayPassword())) {
            bindingResult.rejectValue("oldPassword", "BadCredential");
        }

        if (!StringUtils.equals(form.getNewPassword(), form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "PasswordConfirm");
        }

        if (bindingResult.hasErrors()) {

            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))).build();
        }

        return submitForm(form, context -> {
            try {
                userService.update(user.getId(), u -> {
                    u.setPayPassword(new BCryptPasswordEncoder().encode(form.getNewPassword()));
                    u.getPasswordHistory().add(u.getPayPassword());
                }).ifPresent(u -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities())));

            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/security"));
    }


    @GetMapping("/security/phone")
    public ModelAndView securityPhonePage(final ProfileSecurityPhoneForm form) {
        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form)))))).build();
    }

    @PostMapping("/security/phone")
    public ModelAndView securityPhonePage(@Valid final ProfileSecurityPhoneForm form, BindingResult bindingResult) {

        userService.findByPhone(form.getPhone()).ifPresent(user -> bindingResult.rejectValue("phone", "Account.bind.phoneExist"));

        verifyCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))).build();
        }

        return submitForm(form, context -> {
            try {
                User user = currentUser().orElseThrow(RuntimeException::new);
                userService.update(user.getId(), u -> u.setPhone(form.getPhone())).ifPresent(u -> refreshUser());
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/security"));
    }

    @GetMapping("/security/email")
    public ModelAndView securityEmailPage(final ProfileSecurityEmailForm form) {
        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form)))))).build();
    }

    @PostMapping("/security/email")
    public ModelAndView securityEmailPage(@Valid final ProfileSecurityEmailForm form, BindingResult bindingResult, final ProfileSecurityEmailBindForm bindForm) {

        userService.findByEmail(form.getEmail()).ifPresent(user -> bindingResult.rejectValue("email", "Account.bind.emailExist"));

        verifyCaptcha(form.getCaptcha(), bindingResult);

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))))).build();
        }

        try {
            String code = RandomStringUtils.randomAlphanumeric(8);
            accountService.sendBindEmail(form.getEmail(), code);

            request.getSession().setAttribute(EMAIL_BINDING_SESSION_ATTR, code);
            request.getSession().setAttribute(EMAIL_BINDING_CONTENT_SESSION_ATTR, form.getEmail());

            bindForm.setCaptcha("");

            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(bindForm)))))).build();
        } catch (Exception e) {
            return page(viewHelper.nextWarningOverview("邮箱绑定失败", e.getMessage()).addLink("返回安全设置", "/profile/security")).build();
        }
    }

    @PostMapping("/security/email/bind")
    public ModelAndView securityEmailBindPage(@Valid final ProfileSecurityEmailBindForm form, BindingResult bindingResult) {

        if (Objects.isNull(request.getSession().getAttribute(EMAIL_BINDING_SESSION_ATTR)) || !StringUtils.equals(form.getCaptcha(), (String) request.getSession().getAttribute(EMAIL_BINDING_SESSION_ATTR)) || bindingResult.hasErrors()) {
            return page(viewHelper.nextWarningOverview("邮箱绑定失败", "绑定验证不正确或者已过期").addLink("返回安全设置", "/profile/security")).build();
        }

        return submitForm(form, context -> {
            try {
                User user = currentUser().orElseThrow(RuntimeException::new);
                String email = (String) request.getSession().getAttribute(EMAIL_BINDING_CONTENT_SESSION_ATTR);
                userService.update(user.getId(), u -> u.setEmail(email));
                refreshUser();
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/security"));
    }

    @GetMapping("/security/question")
    public ModelAndView securityQuestionPage(final ProfileSecurityQuestionForm form) {
        return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form))))))
                .addObject("selection_items_question1", QUESTIONS_LIST_1)
                .addObject("selection_items_question2", QUESTIONS_LIST_2)
                .addObject("selection_items_question3", QUESTIONS_LIST_3)
                .build();
    }

    @PostMapping("/security/question")
    public ModelAndView securityQuestionPage(@Valid final ProfileSecurityQuestionForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return page().addComponent(new Grid().setTextContainer().setVerticallyPadded().addItem(new Row().addCol(new Col().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))))))
                    .addObject("selection_items_question1", QUESTIONS_LIST_1)
                    .addObject("selection_items_question2", QUESTIONS_LIST_2)
                    .addObject("selection_items_question3", QUESTIONS_LIST_3)
                    .build();
        }

        return submitForm(form, context -> {
            try {
                User user = currentUser().orElseThrow(RuntimeException::new);

                Optional<AccountSecurityQuestion> securityQuestionOptional = securityQuestionService.findByUserId(user.getId());

                if (securityQuestionOptional.isPresent()) {
                    securityQuestionService.update(securityQuestionOptional.get().getId(), securityQuestion -> {
                        securityQuestion.setQuestion1(form.getQuestion1());
                        securityQuestion.setAnswer1(form.getAnswer1());
                        securityQuestion.setQuestion2(form.getQuestion2());
                        securityQuestion.setAnswer2(form.getAnswer2());
                        securityQuestion.setQuestion3(form.getQuestion3());
                        securityQuestion.setAnswer3(form.getAnswer3());
                    });
                } else {
                    securityQuestionService.create(() -> {
                        AccountSecurityQuestion securityQuestion = securityQuestionService.next();
                        securityQuestion.setUserId(user.getId());
                        securityQuestion.setQuestion1(form.getQuestion1());
                        securityQuestion.setAnswer1(form.getAnswer1());
                        securityQuestion.setQuestion2(form.getQuestion2());
                        securityQuestion.setAnswer2(form.getAnswer2());
                        securityQuestion.setQuestion3(form.getQuestion3());
                        securityQuestion.setAnswer3(form.getAnswer3());
                        return securityQuestion;
                    });
                }
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", "/profile/security"));
    }
}
