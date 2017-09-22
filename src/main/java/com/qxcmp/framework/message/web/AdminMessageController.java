package com.qxcmp.framework.message.web;

import com.google.common.collect.ImmutableList;
import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.message.EmailService;
import com.qxcmp.framework.message.SmsService;
import com.qxcmp.framework.web.QXCMPBackendController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.modules.table.*;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.*;

/**
 * 消息服务后台页面
 *
 * @author Aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/message")
@RequiredArgsConstructor
public class AdminMessageController extends QXCMPBackendController {

    private final EmailService emailService;

    private final SmsService smsService;

    @GetMapping("")
    public ModelAndView messagePage() {
        return page().addComponent(new Overview("消息服务").addComponent(new Table().setSelectable().setStriped()
                .setHeader((AbstractTableHeader) new TableHeader().addRow(new TableRow().addCell(new TableHead("配置项")).addCell(new TableHead("配置值"))))
                .setBody((AbstractTableBody) new TableBody()
                        .addRow(new TableRow().addCell(new TableData("邮件服务 - 主机名")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE))))
                        .addRow(new TableRow().addCell(new TableData("邮件服务 - 端口")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)))))
                        .addRow(new TableRow().addCell(new TableData("邮件服务 - 用户名")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE))))
                        .addRow(new TableRow().addCell(new TableData("邮件服务 - 密码")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD))))
                        .addRow(new TableRow().addCell(new TableData("短信服务 - AccessKey")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE))))
                        .addRow(new TableRow().addCell(new TableData("短信服务 - AccessSecret")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE))))
                        .addRow(new TableRow().addCell(new TableData("短信服务 - EndPoint")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT).orElse(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE))))
                        .addRow(new TableRow().addCell(new TableData("短信服务 - 主题名称")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF).orElse(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE))))
                        .addRow(new TableRow().addCell(new TableData("短信服务 - 短信签名ID")).addCell(new TableData(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_SIGN).orElse(SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE))))
                )))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务")
                .setVerticalMenu(getVerticalMenu(""))
                .build();
    }

    @GetMapping("/email/config")
    public ModelAndView emailConfigPage(final AdminMessageEmailConfigForm form) {

        form.setHost(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
        form.setPort(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)));
        form.setUsername(systemConfigService.getString(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE));
        form.setPassword(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE));
        form.setActivateSubject(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT_DEFAULT_VALUE));
        form.setActivateContent(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT_DEFAULT_VALUE));
        form.setResetSubject(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT_DEFAULT_VALUE));
        form.setResetContent(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT_DEFAULT_VALUE));
        form.setBindingSubject(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT_DEFAULT_VALUE));
        form.setBindingContent(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT_DEFAULT_VALUE));

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "邮件服务配置")
                .setVerticalMenu(getVerticalMenu("邮件服务配置"))
                .build();
    }

    @PostMapping("/email/config")
    public ModelAndView emailConfigPage(@Valid final AdminMessageEmailConfigForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "邮件服务配置")
                    .setVerticalMenu(getVerticalMenu("邮件服务配置"))
                    .build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME, form.getHost());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT, form.getPort());
            systemConfigService.update(SYSTEM_CONFIG_ACCOUNT_ENABLE_USERNAME, form.getUsername());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD, form.getPassword());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_SUBJECT, form.getActivateSubject());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_ACTIVATE_CONTENT, form.getActivateContent());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_SUBJECT, form.getResetSubject());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_RESET_CONTENT, form.getResetContent());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_SUBJECT, form.getBindingSubject());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_EMAIL_ACCOUNT_BINDING_CONTENT, form.getBindingContent());

            emailService.config();
        });
    }


    @GetMapping("/sms/config")
    public ModelAndView smsConfigPage(final AdminMessageSmsConfigForm form) {

        form.setAccessKey(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE));
        form.setAccessSecret(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE));
        form.setEndPoint(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT).orElse(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE));
        form.setTopicRef(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF).orElse(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE));
        form.setSign(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_SIGN).orElse(SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE));
        form.setCaptchaTemplate(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE).orElse(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE_DEFAULT_VALUE));

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信服务配置")
                .setVerticalMenu(getVerticalMenu("短信服务配置"))
                .build();
    }

    @PostMapping("/sms/config")
    public ModelAndView smsConfigPage(@Valid final AdminMessageSmsConfigForm form, BindingResult bindingResult,
                                      @RequestParam(value = "add_templates", required = false) boolean addTemplates,
                                      @RequestParam(value = "remove_templates", required = false) Integer removeTemplates) {

        if (addTemplates) {
            form.getTemplates().add(new SmsTemplate());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信服务配置")
                    .setVerticalMenu(getVerticalMenu("短信服务配置"))
                    .build();
        }

        if (Objects.nonNull(removeTemplates)) {
            form.getTemplates().remove(removeTemplates.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信服务配置")
                    .setVerticalMenu(getVerticalMenu("短信服务配置"))
                    .build();
        }

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信服务配置")
                    .setVerticalMenu(getVerticalMenu("短信服务配置"))
                    .build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY, form.getAccessKey());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET, form.getAccessSecret());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT, form.getEndPoint());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF, form.getTopicRef());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_SIGN, form.getSign());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE, form.getCaptchaTemplate());

            smsService.config();
        });
    }

    @GetMapping("/sms/verify")
    public ModelAndView smsVerifyPage(final AdminMessageSmsVerifyForm form) {
        SmsMessageParameter smsMessageParameter = new SmsMessageParameter();
        smsMessageParameter.setKey("captcha");
        smsMessageParameter.setValue(RandomStringUtils.randomNumeric(6));

        form.setTemplateCode(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE).orElse(""));
        form.getParameters().add(smsMessageParameter);
        form.getPhones().add(currentUser().orElseThrow(null).getPhone());

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信发送验证")
                .setVerticalMenu(getVerticalMenu("短信发送验证"))
                .build();
    }

    @PostMapping("/sms/verify")
    public ModelAndView smsVerifyPage(@Valid final AdminMessageSmsVerifyForm form, BindingResult bindingResult,
                                      @RequestParam(value = "add_phones", required = false) boolean addPhones,
                                      @RequestParam(value = "remove_phones", required = false) Integer removePhones,
                                      @RequestParam(value = "add_parameters", required = false) boolean addParameters,
                                      @RequestParam(value = "remove_parameters", required = false) Integer removeParameters) {

        if (addPhones) {
            form.getPhones().add("");
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信发送验证")
                    .setVerticalMenu(getVerticalMenu("短信发送验证"))
                    .build();
        }

        if (Objects.nonNull(removePhones)) {
            form.getPhones().remove(removePhones.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信发送验证")
                    .setVerticalMenu(getVerticalMenu("短信发送验证"))
                    .build();
        }

        if (addParameters) {
            form.getParameters().add(new SmsMessageParameter());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信发送验证")
                    .setVerticalMenu(getVerticalMenu("短信发送验证"))
                    .build();
        }

        if (Objects.nonNull(removeParameters)) {
            form.getParameters().remove(removeParameters.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信发送验证")
                    .setVerticalMenu(getVerticalMenu("短信发送验证"))
                    .build();
        }

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", QXCMP_BACKEND_URL, "消息服务", QXCMP_BACKEND_URL + "/message", "短信发送验证")
                    .setVerticalMenu(getVerticalMenu("短信发送验证"))
                    .build();
        }

        return submitForm(form, context -> {
            try {
                smsService.send(form.getPhones(), form.getTemplateCode(), stringStringMap -> {
                    form.getParameters().forEach(smsMessageParameter -> stringStringMap.put(smsMessageParameter.getKey(), smsMessageParameter.getValue()));
                });
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
    }

    private List<String> getVerticalMenu(String activeItem) {
        return ImmutableList.of(activeItem, "邮件服务配置", QXCMP_BACKEND_URL + "/message/email/config", "短信服务配置", QXCMP_BACKEND_URL + "/message/sms/config", "短信发送验证", QXCMP_BACKEND_URL + "/message/sms/verify");
    }
}
