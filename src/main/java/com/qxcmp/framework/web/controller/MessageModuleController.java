package com.qxcmp.framework.web.controller;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.message.EmailService;
import com.qxcmp.framework.message.SmsService;
import com.qxcmp.framework.view.nav.Navigation;
import com.qxcmp.framework.web.QXCMPBackendController2;
import com.qxcmp.framework.web.form.AdminMessageEmailConfigForm;
import com.qxcmp.framework.web.form.AdminMessageSmsConfigForm;
import com.qxcmp.framework.web.form.AdminMessageSmsTestForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.qxcmp.framework.core.QXCMPConfiguration.*;

/**
 * 消息管理模块页面路由
 *
 * @author aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/message")
@RequiredArgsConstructor
public class MessageModuleController extends QXCMPBackendController2 {

    private final EmailService emailService;

    private final SmsService smsService;

    @GetMapping
    public ModelAndView message() {
        return builder().setTitle("消息服务")
                .setResult("消息服务系统配置", "")
                .addDivider()
                .addDictionaryView(dictionaryViewBuilder -> dictionaryViewBuilder
                        .title("邮件服务配置")
                        .dictionary("主机名", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE))
                        .dictionary("端口", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)))
                        .dictionary("用户名", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE))
                        .dictionary("密码", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE))
                        .dictionary("账户激活邮件主题", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT_DEFAULT_VALUE))
                        .dictionary("账户激活邮件模板", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT_DEFAULT_VALUE))
                        .dictionary("密码重置邮件主题", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT_DEFAULT_VALUE))
                        .dictionary("密码重置邮件模板", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT_DEFAULT_VALUE))
                        .dictionary("账户绑定邮件模板", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT_DEFAULT_VALUE))
                        .dictionary("账户绑定邮件模板", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT_DEFAULT_VALUE))
                )
                .addDivider(false)
                .addDictionaryView(dictionaryViewBuilder -> dictionaryViewBuilder
                        .title("短信服务配置")
                        .dictionary("AccessKey", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE))
                        .dictionary("AccessSecret", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE))
                        .dictionary("EndPoint", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF).orElse(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE))
                        .dictionary("主题名称", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT).orElse(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE))
                        .dictionary("短信签名ID", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_SIGN).orElse(SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE))
                        .dictionary("验证码短信模板CODE", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE).orElse(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE_DEFAULT_VALUE))
                )
                .addNavigation(Navigation.Type.NORMAL, "消息服务")
                .build();
    }

    @GetMapping("/email/config")
    public ModelAndView emailGet(AdminMessageEmailConfigForm form) {
        form.setHost(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
        form.setPort(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)));
        form.setUsername(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE));
        form.setPassword(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE));
        form.setActivateSubject(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT_DEFAULT_VALUE));
        form.setActivateContent(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT_DEFAULT_VALUE));
        form.setResetSubject(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT_DEFAULT_VALUE));
        form.setResetContent(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT_DEFAULT_VALUE));
        form.setBindingSubject(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT_DEFAULT_VALUE));
        form.setBindingContent(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT_DEFAULT_VALUE));
        return builder().setTitle("邮件服务配置")
                .setFormView(form)
                .addNavigation("邮件服务配置", Navigation.Type.NORMAL, "消息服务")
                .build();
    }

    @PostMapping("/email/config")
    public ModelAndView emailPost(@Valid @ModelAttribute("object") AdminMessageEmailConfigForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改邮箱服务配置", context -> {
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME, form.getHost());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT, form.getPort());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME, form.getUsername());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD, form.getPassword());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT, form.getActivateSubject());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT, form.getActivateContent());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT, form.getResetContent());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT, form.getResetContent());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT, form.getBindingSubject());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT, form.getBindingContent());
            emailService.config();
        }).build();
    }

    @GetMapping("/sms/config")
    public ModelAndView smsGet(AdminMessageSmsConfigForm form) {
        form.setAccessKey(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE));
        form.setAccessSecret(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE));
        form.setTopicRef(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF).orElse(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE));
        form.setEndPoint(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT).orElse(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE));
        form.setSign(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_SIGN).orElse(SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE));
        form.setCaptchaTemplate(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE).orElse(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE_DEFAULT_VALUE));
        return builder().setTitle("短信服务配置")
                .setFormView(form)
                .addNavigation("短信服务配置", Navigation.Type.NORMAL, "消息服务")
                .build();
    }

    @PostMapping("/sms/config")
    public ModelAndView smsPost(@Valid @ModelAttribute("object") AdminMessageSmsConfigForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("修改短信服务配置", context -> {
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY, form.getAccessKey());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET, form.getAccessSecret());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT, form.getEndPoint());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF, form.getTopicRef());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_SIGN, form.getSign());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE, form.getCaptchaTemplate());
            smsService.config();
        }).build();
    }

    @GetMapping("/sms/test")
    public ModelAndView smsTestGet(AdminMessageSmsTestForm form) {

        form.setParameter("captcha");
        form.setContent(RandomStringUtils.randomNumeric(6));

        return builder().setTitle("短信服务测试")
                .setFormView(form)
                .addNavigation("短信服务测试", Navigation.Type.NORMAL, "消息服务")
                .build();
    }

    @PostMapping("/sms/test")
    public ModelAndView smsTestPost(@Valid @ModelAttribute("object") AdminMessageSmsTestForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return builder().setFormView(form).build();
        }

        return action("发送测试短信", context -> {
            try {
                smsService.send(Arrays.stream(form.getPhone().split("\n")).map(String::trim).collect(Collectors.toList()), form.getTemplateCode(), para -> para.put(form.getParameter(), form.getContent()));
            } catch (Exception e) {
                throw new ActionException("测试短信发送失败，请检查短信服务配置是否正确。原因：" + e.getMessage());
            }
        }).build();
    }
}
