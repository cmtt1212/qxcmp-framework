package com.qxcmp.framework.message.web;

import com.qxcmp.framework.audit.ActionException;
import com.qxcmp.framework.message.*;
import com.qxcmp.framework.user.User;
import com.qxcmp.framework.web.QXCMPController;
import com.qxcmp.framework.web.view.elements.segment.Segment;
import com.qxcmp.framework.web.view.views.Overview;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

import static com.qxcmp.framework.core.QXCMPConfiguration.QXCMP_BACKEND_URL;
import static com.qxcmp.framework.core.QXCMPNavigationConfiguration.*;
import static com.qxcmp.framework.core.QXCMPSystemConfigConfiguration.*;

/**
 * 消息服务后台页面
 *
 * @author Aaric
 */
@Controller
@RequestMapping(QXCMP_BACKEND_URL + "/message")
@RequiredArgsConstructor
public class AdminMessageController extends QXCMPController {

    private final EmailService emailService;

    private final SmsService smsService;

    private final SmsTemplateService smsTemplateService;

    private final SiteNotificationService siteNotificationService;

    @GetMapping("")
    public ModelAndView messagePage() {
        return page().addComponent(new Overview("消息服务")
                .addComponent(convertToTable(stringStringMap -> {
                    stringStringMap.put("邮件服务 - 主机名", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_HOSTNAME_DEFAULT_VALUE));
                    stringStringMap.put("邮件服务 - 端口", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT).orElse(String.valueOf(SYSTEM_CONFIG_MESSAGE_EMAIL_PORT_DEFAULT_VALUE)));
                    stringStringMap.put("邮件服务 - 用户名", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_USERNAME_DEFAULT_VALUE));
                    stringStringMap.put("邮件服务 - 密码", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD).orElse(SYSTEM_CONFIG_MESSAGE_EMAIL_PASSWORD_DEFAULT_VALUE));
                    stringStringMap.put("短信服务 - AccessKey", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE));
                    stringStringMap.put("短信服务 - AccessSecret", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE));
                    stringStringMap.put("短信服务 - EndPoint", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT).orElse(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE));
                    stringStringMap.put("短信服务 - 主题名称", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF).orElse(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE));
                    stringStringMap.put("短信服务 - 短信签名ID", systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_SIGN).orElse(SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE));
                })))
                .setBreadcrumb("控制台", "", "消息服务")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, "")
                .build();
    }

    @GetMapping("/sms/send")
    public ModelAndView smsSendPage(final AdminMessageSmsSendForm form) {
        SmsMessageParameter smsMessageParameter = new SmsMessageParameter();
        smsMessageParameter.setKey("captcha");
        smsMessageParameter.setValue(RandomStringUtils.randomNumeric(6));

        form.setTemplateCode(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE).orElse(""));
        form.getParameters().add(smsMessageParameter);
        form.getPhones().add(currentUser().orElseThrow(null).getPhone());

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", "", "消息服务", "message", "短信发送服务")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SEND)
                .build();
    }

    @PostMapping("/sms/send")
    public ModelAndView smsSendPage(@Valid final AdminMessageSmsSendForm form, BindingResult bindingResult,
                                    @RequestParam(value = "add_phones", required = false) boolean addPhones,
                                    @RequestParam(value = "remove_phones", required = false) Integer removePhones,
                                    @RequestParam(value = "add_parameters", required = false) boolean addParameters,
                                    @RequestParam(value = "remove_parameters", required = false) Integer removeParameters) {

        if (addPhones) {
            form.getPhones().add("");
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SEND)
                    .build();
        }

        if (Objects.nonNull(removePhones)) {
            form.getPhones().remove(removePhones.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SEND)
                    .build();
        }

        if (addParameters) {
            form.getParameters().add(new SmsMessageParameter());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SEND)
                    .build();
        }

        if (Objects.nonNull(removeParameters)) {
            form.getParameters().remove(removeParameters.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SEND)
                    .build();
        }

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SEND)
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

    @GetMapping("/email/send")
    public ModelAndView emailSendPage(final AdminMessageEmailSendForm form) {
        form.getTo().add("");
        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", "", "消息服务", "message", "邮件发送服务")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SEND)
                .build();
    }

    @PostMapping("/email/send")
    public ModelAndView emailSendPage(@Valid final AdminMessageEmailSendForm form, BindingResult bindingResult,
                                      @RequestParam(value = "add_to", required = false) boolean addTo,
                                      @RequestParam(value = "remove_to", required = false) Integer removeTo,
                                      @RequestParam(value = "add_cc", required = false) boolean addCc,
                                      @RequestParam(value = "remove_cc", required = false) Integer removeCc) {

        if (addTo) {
            form.getTo().add("");
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "邮件发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SEND)
                    .build();
        }

        if (Objects.nonNull(removeTo)) {
            form.getTo().remove(removeTo.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "邮件发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SEND)
                    .build();
        }

        if (addCc) {
            form.getCc().add("");
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "邮件发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SEND)
                    .build();
        }

        if (Objects.nonNull(removeCc)) {
            form.getCc().remove(removeCc.intValue());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "邮件发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SEND)
                    .build();
        }

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "邮件发送服务")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SEND)
                    .build();
        }

        return submitForm(form, context -> {
            try {
                emailService.send(mimeMessageHelper -> {
                    mimeMessageHelper.setSubject(form.getSubject());
                    form.getTo().forEach(s -> {
                        try {
                            mimeMessageHelper.addTo(s);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    });

                    form.getCc().forEach(s -> {
                        try {
                            mimeMessageHelper.addCc(s);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    });

                    mimeMessageHelper.setText(form.getContent());
                });
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        });
    }

    @GetMapping("/email/settings")
    public ModelAndView emailSettingsPage(final AdminMessageEmailSettingsForm form) {

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
                .setBreadcrumb("控制台", "", "消息服务", "message", "邮件服务配置")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SETTINGS)
                .build();
    }

    @PostMapping("/email/settings")
    public ModelAndView emailSettingsPage(@Valid final AdminMessageEmailSettingsForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "邮件服务配置")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_EMAIL_SETTINGS)
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


    @GetMapping("/sms/settings")
    public ModelAndView smsSettingsPage(final AdminMessageSmsSettingsForm form) {

        form.setAccessKey(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY_DEFAULT_VALUE));
        form.setAccessSecret(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET).orElse(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET_DEFAULT_VALUE));
        form.setEndPoint(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT).orElse(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT_DEFAULT_VALUE));
        form.setTopicRef(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF).orElse(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF_DEFAULT_VALUE));
        form.setSign(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_SIGN).orElse(SYSTEM_CONFIG_MESSAGE_SMS_SIGN_DEFAULT_VALUE));
        form.setCaptchaTemplate(systemConfigService.getString(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE).orElse(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE_DEFAULT_VALUE));
        form.setTemplates(smsTemplateService.findAll());

        return page().addComponent(new Segment().addComponent(convertToForm(form)))
                .setBreadcrumb("控制台", "", "消息服务", "message", "短信服务配置")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SETTINGS)
                .build();
    }

    @PostMapping("/sms/settings")
    public ModelAndView smsSettingsPage(@Valid final AdminMessageSmsSettingsForm form, BindingResult bindingResult,
                                        @RequestParam(value = "add_templates", required = false) boolean addTemplates,
                                        @RequestParam(value = "remove_templates", required = false) Integer removeTemplates) {

        if (addTemplates) {
            form.getTemplates().add(smsTemplateService.next());
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信服务配置")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SETTINGS)
                    .build();
        }

        if (Objects.nonNull(removeTemplates)) {
            try {
                smsTemplateService.remove(form.getTemplates().remove(removeTemplates.intValue()));
            } catch (Exception ignored) {

            }
            return page().addComponent(new Segment().addComponent(convertToForm(form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信服务配置")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SETTINGS)
                    .build();
        }

        if (bindingResult.hasErrors()) {
            return page().addComponent(new Segment().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form))))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "短信服务配置")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SMS_SETTINGS)
                    .build();
        }

        return submitForm(form, context -> {
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_KEY, form.getAccessKey());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_ACCESS_SECRET, form.getAccessSecret());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_END_POINT, form.getEndPoint());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_TOPIC_REF, form.getTopicRef());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_SIGN, form.getSign());
            systemConfigService.update(SYSTEM_CONFIG_MESSAGE_SMS_CAPTCHA_TEMPLATE_CODE, form.getCaptchaTemplate());

            form.getTemplates().stream()
                    .filter(smsTemplate -> StringUtils.isNotBlank(smsTemplate.getId()) && StringUtils.isNotBlank(smsTemplate.getTitle()) && StringUtils.isNotBlank(smsTemplate.getTemplate()))
                    .forEach(smsTemplateService::save);

            smsService.config();
        });
    }

    @GetMapping("/site/notification")
    public ModelAndView siteNotificationPage(Pageable pageable) {
        return page().addComponent(convertToTable(pageable, siteNotificationService))
                .setBreadcrumb("控制台", "", "消息服务", "message", "网站通知服务")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SITE_NOTIFICATION)
                .build();
    }

    @GetMapping("/site/notification/new")
    public ModelAndView siteNotificationNewPage(final AdminMessageSiteNotificationNewForm form) {

        form.setDateStart(new Date());
        form.setDateEnd(DateTime.now().plusDays(1).toDate());

        return page().addComponent(convertToForm(form))
                .setBreadcrumb("控制台", "", "消息服务", "message", "网站通知服务", "message/site/notification", "新建网站通知")
                .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SITE_NOTIFICATION)
                .build();
    }

    @PostMapping("/site/notification/new")
    public ModelAndView siteNotificationNewPage(@Valid final AdminMessageSiteNotificationNewForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return page().addComponent(convertToForm(form).setErrorMessage(convertToErrorMessage(bindingResult, form)))
                    .setBreadcrumb("控制台", "", "消息服务", "message", "网站通知服务", "message/site/notification", "新建网站通知")
                    .setVerticalNavigation(NAVIGATION_ADMIN_MESSAGE, NAVIGATION_ADMIN_MESSAGE_SITE_NOTIFICATION)
                    .build();
        }

        return submitForm(form, context -> {
            try {
                siteNotificationService.create(() -> {
                    SiteNotification siteNotification = siteNotificationService.next();
                    siteNotification.setOwnerId(currentUser().map(User::getId).orElse(""));
                    siteNotification.setType(form.getType());
                    siteNotification.setDateStart(form.getDateStart());
                    siteNotification.setDateEnd(form.getDateEnd());
                    siteNotification.setContent(form.getContent());
                    return siteNotification;
                });
            } catch (Exception e) {
                throw new ActionException(e.getMessage(), e);
            }
        }, (stringObjectMap, overview) -> overview.addLink("返回", QXCMP_BACKEND_URL + "/message/site/notification").addLink("继续新建", QXCMP_BACKEND_URL + "/message/site/notification/new"));
    }
}
