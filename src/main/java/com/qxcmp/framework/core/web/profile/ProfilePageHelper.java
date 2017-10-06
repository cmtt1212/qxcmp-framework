package com.qxcmp.framework.core.web.profile;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.account.AccountService;
import com.qxcmp.framework.account.username.AccountSecurityQuestionService;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.user.UserService;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.support.utils.ViewHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class ProfilePageHelper {

    private static final String EMAIL_BINDING_SESSION_ATTR = "EMAIL_BINDING_CAPTCHA";
    private static final String EMAIL_BINDING_CONTENT_SESSION_ATTR = "EMAIL_BINDING_CONTENT";

    private static final List<String> QUESTIONS_LIST_1 = ImmutableList.of("您高中三年级班主任的名字", "您小学六年级班主任的名字", "您大学时的学号", "您大学本科时的上/下铺叫什么名字", "您大学的导师叫什么名字");
    private static final List<String> QUESTIONS_LIST_2 = ImmutableList.of("您父母称呼您的昵称", "您出生的医院名称", "您最好的朋友叫什么名字", "您母亲的姓名是", "您配偶的生日是");
    private static final List<String> QUESTIONS_LIST_3 = ImmutableList.of("您第一个宠物的名字", "您的第一任男朋友/女朋友姓名", "您第一家任职的公司名字");

    private final UserService userService;
    private final AccountSecurityQuestionService securityQuestionService;
    private final AccountService accountService;
    private final ViewHelper viewHelper;

    public Component nextProfileInfoComponent(AdminProfileInfoForm form) {
        User user = userService.currentUser();
        form.setPortrait(user.getPortrait());
        form.setName(user.getName());
        form.setNickname(user.getNickname());
        form.setPersonalizedSignature(user.getPersonalizedSignature());
        return new Segment().addComponent(viewHelper.nextForm(form));
    }

    public Component nextProfileInfoComponent(AdminProfileInfoForm form, BindingResult bindingResult) {
        User user = userService.currentUser();
        form.setPortrait(user.getPortrait());
        form.setName(user.getName());
        form.setNickname(user.getNickname());
        form.setPersonalizedSignature(user.getPersonalizedSignature());
        return new Segment().addComponent(viewHelper.nextForm(form).setErrorMessage(viewHelper.nextFormErrorMessage(bindingResult, form)));
    }

    public Optional<User> executeProfileInfoSubmit(AdminProfileInfoForm form) throws ActionException {
        try {
            User user = userService.currentUser();
            return userService.update(user.getId(), u -> {
                u.setPortrait(form.getPortrait());
                u.setName(form.getName());
                u.setNickname(form.getNickname());
                u.setPersonalizedSignature(form.getPersonalizedSignature());
            });
        } catch (Exception e) {
            throw new ActionException(e.getMessage(), e);
        }
    }
}
