package com.qxcmp.framework.core.web;

import com.qxcmp.framework.account.username.AccountSecurityQuestionService;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.container.TextContainer;
import com.qxcmp.framework.web.view.elements.header.HeaderType;
import com.qxcmp.framework.web.view.elements.header.PageHeader;
import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.table.*;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/profile")
@RequiredArgsConstructor
public class AdminProfilePageController extends QXCMPBackendController {

    private final AccountSecurityQuestionService accountSecurityQuestionService;

    @GetMapping("/info")
    public ModelAndView infoPage(final AdminProfileInfoForm form) {
        User user = currentUser().orElseThrow(RuntimeException::new);

        form.setPortrait(user.getPortrait());
        form.setName(user.getName());
        form.setNickname(user.getNickname());
        form.setPersonalizedSignature(user.getPersonalizedSignature());

        return page().addComponent(new TextContainer().addComponent(new Segment().addComponent(convertToForm(form))))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "个人中心", "", "基本资料")
                .build();
    }

    @PostMapping("/info")
    public ModelAndView infoPage(@Valid final AdminProfileInfoForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new TextContainer().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "个人中心", "", "基本资料")
                    .build();
        }

        return submitForm(form, context -> {
            try {
                User user = currentUser().orElseThrow(RuntimeException::new);
                userService.update(user.getId(), u -> {
                    u.setPortrait(form.getPortrait());
                    u.setName(form.getName());
                    u.setNickname(form.getNickname());
                    u.setPersonalizedSignature(form.getPersonalizedSignature());
                }).ifPresent(u -> refreshUser());
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
    }

    @GetMapping("/security")
    public ModelAndView securityPage() {
        User user = currentUser().orElseThrow(RuntimeException::new);

        boolean hasSecurityQuestion = accountSecurityQuestionService.findByUserId(user.getId()).map(accountSecurityQuestion -> StringUtils.isNotBlank(accountSecurityQuestion.getQuestion1()) && StringUtils.isNotBlank(accountSecurityQuestion.getQuestion2()) && StringUtils.isNotBlank(accountSecurityQuestion.getQuestion3())).orElse(false);

        return page()
                .addComponent(new PageHeader(HeaderType.H2, "安全设置").setDividing())
                .addComponent(new Table().setBasic().setHeader((AbstractTableHeader) new TableHeader()
                        .addRow(new TableRow().addCell(new TableHead("登录密码").setAlignment(Alignment.CENTER)).addCell(new TableData("安全性高的密码可以使帐号更安全")).addCell(new TableData(StringUtils.isNotBlank(user.getPassword()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Anchor("修改", QXCMP_BACKEND_URL + "/profile/security/password")).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("手机绑定").setAlignment(Alignment.CENTER)).addCell(new TableData("您的手机号可以直接用于登录、找回密码等")).addCell(new TableData(StringUtils.isNotBlank(user.getPhone()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Anchor("修改", QXCMP_BACKEND_URL + "/profile/security/phone")).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("邮箱绑定").setAlignment(Alignment.CENTER)).addCell(new TableData("您的邮箱可以直接用于登录、找回密码等")).addCell(new TableData(StringUtils.isNotBlank(user.getEmail()) ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Anchor("修改", QXCMP_BACKEND_URL + "/profile/security/email")).setAlignment(Alignment.CENTER)))
                        .addRow(new TableRow().addCell(new TableHead("密保问题").setAlignment(Alignment.CENTER)).addCell(new TableData("建议您设置三个容易记住，且最不容易被他人获取的问题及答案，更有效保障您的密码安全")).addCell(new TableData(hasSecurityQuestion ? new Icon("check circle").setColor(Color.GREEN) : new Icon("warning circle").setColor(Color.ORANGE)).setAlignment(Alignment.CENTER)).addCell(new TableData(new Anchor("修改", QXCMP_BACKEND_URL + "/profile/security/question")).setAlignment(Alignment.CENTER)))
                ))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "个人中心", "", "安全设置")
                .build();
    }
}
