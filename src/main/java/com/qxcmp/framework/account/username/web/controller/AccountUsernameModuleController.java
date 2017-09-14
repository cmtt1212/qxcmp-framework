package com.qxcmp.framework.account.username.web.controller;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.account.username.SecurityQuestion;
import com.qxcmp.framework.account.username.SecurityQuestionService;
import com.qxcmp.framework.account.username.web.form.AdminAccountQuestionForm;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;

/**
 * 用户名注册模块后端页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/account/question")
@RequiredArgsConstructor
public class AccountUsernameModuleController extends QXCMPBackendController2 {

    private static final List<String> QUESTIONS_LIST_1 = ImmutableList.of("您高中三年级班主任的名字", "您小学六年级班主任的名字", "您大学时的学号", "您大学本科时的上/下铺叫什么名字", "您大学的导师叫什么名字");
    private static final List<String> QUESTIONS_LIST_2 = ImmutableList.of("您父母称呼您的昵称", "您出生的医院名称", "您最好的朋友叫什么名字", "您母亲的姓名是", "您配偶的生日是");
    private static final List<String> QUESTIONS_LIST_3 = ImmutableList.of("您第一个宠物的名字", "您的第一任男朋友/女朋友姓名", "您第一家任职的公司名字");

    private final SecurityQuestionService securityQuestionService;

    @GetMapping
    public ModelAndView set(final AdminAccountQuestionForm form) {
        return builder().setTitle("设置密保")
                .setFormView(form, QUESTIONS_LIST_1, QUESTIONS_LIST_2, QUESTIONS_LIST_3)
                .addNavigation("我的密保", Navigation.Type.NORMAL, "个人中心")
                .build();
    }

    @PostMapping
    public ModelAndView set(@Valid @ModelAttribute(FORM_OBJECT) AdminAccountQuestionForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setTitle("设置密保")
                    .setFormView(form, QUESTIONS_LIST_1, QUESTIONS_LIST_2, QUESTIONS_LIST_3)
                    .build();
        }

        try {
            Optional<SecurityQuestion> securityQuestionOptional = securityQuestionService.findByUserId(currentUser().getId());

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
                    SecurityQuestion securityQuestion = securityQuestionService.next();
                    securityQuestion.setUserId(currentUser().getId());
                    securityQuestion.setQuestion1(form.getQuestion1());
                    securityQuestion.setAnswer1(form.getAnswer1());
                    securityQuestion.setQuestion2(form.getQuestion2());
                    securityQuestion.setAnswer2(form.getAnswer2());
                    securityQuestion.setQuestion3(form.getQuestion3());
                    securityQuestion.setAnswer3(form.getAnswer3());
                    return securityQuestion;
                });
            }

            return builder()
                    .setResult("设置密保成功", "")
                    .setResultNavigation("返回", QXCMP_BACKEND_URL + "/account/question")
                    .build();
        } catch (Exception e) {
            return error(HttpStatus.BAD_GATEWAY, e.getMessage()).build();
        }
    }

}
