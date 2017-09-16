package com.qxcmp.framework.web.view.support.utils;

import com.google.common.base.CaseFormat;
import com.qxcmp.framework.web.view.annotation.form.*;
import com.qxcmp.framework.web.view.elements.icon.Icon;
import com.qxcmp.framework.web.view.elements.list.List;
import com.qxcmp.framework.web.view.elements.list.item.IconHeaderItem;
import com.qxcmp.framework.web.view.elements.message.ErrorMessage;
import com.qxcmp.framework.web.view.modules.form.AbstractForm;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
@RequiredArgsConstructor
public class FormHelper {

    public static final String SELF_ACTION = "$SELF";

    private final ApplicationContext applicationContext;

    /**
     * 将表单错误对象转换为错误消息组件
     *
     * @param bindingResult 错误对象
     * @param object        表单对象
     * @return 错误消息组件
     */
    public ErrorMessage convertToErrorMessage(BindingResult bindingResult, Object object) {
        List errLists = new List();
        bindingResult.getAllErrors().forEach(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;

                String fieldName = fieldError.getField();

                for (Field field : object.getClass().getDeclaredFields()) {
                    if (field.getName().equals(fieldName)) {
                        for (Annotation annotation : field.getAnnotations()) {
                            for (Method method : annotation.getClass().getDeclaredMethods()) {
                                if (method.getName().equals("value")) {
                                    try {
                                        fieldName = method.invoke(annotation).toString();
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                    }

                                    break;
                                }
                            }
                        }

                        break;
                    }
                }

                String errMessage = "";

                for (String code : fieldError.getCodes()) {
                    try {
                        String message = applicationContext.getMessage(code, null, null);
                        if (StringUtils.isNotBlank(message)) {
                            errMessage = message;
                            break;
                        }
                    } catch (Exception ignored) {

                    }
                }

                if (StringUtils.isBlank(errMessage)) {
                    errMessage = fieldError.getDefaultMessage();
                }


                errLists.addItem(new IconHeaderItem(new Icon("warning sign"), fieldName, errMessage));
            }
        });
        return (ErrorMessage) new ErrorMessage("你输入的信息有误，请检查后重试", errLists).setCloseable();
    }

    /**
     * 将一个对象转换为表单
     *
     * @param object 对象
     * @return 转换后的表单
     */
    public AbstractForm convert(Object object) {

        Form annotation = object.getClass().getAnnotation(Form.class);
        checkNotNull(annotation, "No Form definition in " + object.getClass().getName());

        final AbstractForm form = createForm(object, annotation);

        configFormField(form, object);

        return form;
    }

    private AbstractForm createForm(Object object, Form annotation) {

        String formName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, object.getClass().getSimpleName()).replaceAll("-form", "").replaceAll("-", ".");
        String objectName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, object.getClass().getSimpleName());

        final AbstractForm form = new com.qxcmp.framework.web.view.modules.form.Form();

        form.setName(annotation.name());
        form.setMethod(annotation.method());
        form.setEnctype(annotation.enctype());
        form.setDisableAutoComplete(annotation.disableAutoComplete());
        form.setTarget(annotation.target());
        form.setSize(annotation.size());
        form.setInverted(annotation.inverted());

        if (StringUtils.isBlank(annotation.objectName())) {
            form.setObject(objectName);
        } else {
            form.setObject(annotation.objectName());
        }

        if (StringUtils.isBlank(annotation.action())) {
            form.setAction("/" + formName.replaceAll("\\.", "/"));
        } else if (StringUtils.equals(annotation.action(), SELF_ACTION)) {
            form.setAction("");
        } else {
            form.setAction(annotation.action());
        }

        if (StringUtils.isNotBlank(annotation.submitText())) {
            form.setSubmitButton(annotation.submitText());
        }

        if (StringUtils.isNotBlank(annotation.submitIcon())) {
            form.setSubmitButton(StringUtils.isBlank(annotation.submitText()) ? "提交" : annotation.submitText(), annotation.submitIcon());
        }

        return form;
    }

    private void configFormField(AbstractForm form, Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof TextInputField) {
                    addTextInputField(form, field, (TextInputField) annotation);
                } else if (annotation instanceof PasswordField) {
                    addPasswordInputField(form, field, (PasswordField) annotation);
                } else if (annotation instanceof TextAreaField) {
                    addTextAreaField(form, field, (TextAreaField) annotation);
                } else if (annotation instanceof ImageCaptchaField) {
                    addImageCaptchaField(form, field, (ImageCaptchaField) annotation);
                } else if (annotation instanceof PhoneCaptchaField) {
                    addPhoneCaptchaField(form, field, (PhoneCaptchaField) annotation);
                } else if (annotation instanceof EmailField) {
                    addEmailField(form, field, (EmailField) annotation);
                } else if (annotation instanceof PhoneField) {
                    addPhoneField(form, field, (PhoneField) annotation);
                } else if (annotation instanceof AvatarField) {
                    addAvatarField(form, field, (AvatarField) annotation);
                }
            }
        }
    }

    private void addTextInputField(AbstractForm form, Field field, TextInputField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.TextInputField textInputField = new com.qxcmp.framework.web.view.modules.form.field.TextInputField();

        textInputField.setName(field.getName());
        textInputField.setLabel(annotation.value());
        textInputField.setTooltip(annotation.tooltip());
        textInputField.setRequired(annotation.required());
        textInputField.setDisableAutoComplete(annotation.disableAutoComplete());
        textInputField.setAutoFocus(annotation.autoFocus());
        textInputField.setReadOnly(annotation.readOnly());
        textInputField.setPlaceholder(annotation.placeholder());
        textInputField.setMaxLength(annotation.maxLength());

        form.addItem(textInputField, annotation.section());
    }

    private void addPasswordInputField(AbstractForm form, Field field, PasswordField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.PasswordField passwordField = new com.qxcmp.framework.web.view.modules.form.field.PasswordField();

        passwordField.setName(field.getName());
        passwordField.setLabel(annotation.value());
        passwordField.setTooltip(annotation.tooltip());
        passwordField.setRequired(annotation.required());
        passwordField.setDisableAutoComplete(annotation.disableAutoComplete());
        passwordField.setAutoFocus(annotation.autoFocus());
        passwordField.setReadOnly(annotation.readOnly());
        passwordField.setPlaceholder(annotation.placeholder());
        passwordField.setMaxLength(annotation.maxLength());

        form.addItem(passwordField, annotation.section());
    }

    private void addTextAreaField(AbstractForm form, Field field, TextAreaField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.TextAreaField textAreaField = new com.qxcmp.framework.web.view.modules.form.field.TextAreaField();

        textAreaField.setName(field.getName());
        textAreaField.setLabel(annotation.value());
        textAreaField.setTooltip(annotation.tooltip());
        textAreaField.setRequired(annotation.required());
        textAreaField.setDisableAutoComplete(annotation.disableAutoComplete());
        textAreaField.setAutoFocus(annotation.autoFocus());
        textAreaField.setReadOnly(annotation.readOnly());
        textAreaField.setPlaceholder(annotation.placeholder());
        textAreaField.setMaxLength(annotation.maxLength());
        textAreaField.setRows(annotation.rows());

        form.addItem(textAreaField, annotation.section());
    }

    private void addImageCaptchaField(AbstractForm form, Field field, ImageCaptchaField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.ImageCaptchaField imageCaptchaField = new com.qxcmp.framework.web.view.modules.form.field.ImageCaptchaField();

        imageCaptchaField.setName(field.getName());
        imageCaptchaField.setLabel(annotation.value());
        imageCaptchaField.setTooltip(annotation.tooltip());
        imageCaptchaField.setRequired(annotation.required());
        imageCaptchaField.setDisableAutoComplete(annotation.disableAutoComplete());
        imageCaptchaField.setAutoFocus(annotation.autoFocus());
        imageCaptchaField.setReadOnly(annotation.readOnly());
        imageCaptchaField.setPlaceholder(annotation.placeholder());
        imageCaptchaField.setCaptchaUrl(annotation.captchaUrl());

        form.addItem(imageCaptchaField, annotation.section());
    }

    private void addPhoneCaptchaField(AbstractForm form, Field field, PhoneCaptchaField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.PhoneCaptchaField phoneCaptchaField = new com.qxcmp.framework.web.view.modules.form.field.PhoneCaptchaField();

        phoneCaptchaField.setName(field.getName());
        phoneCaptchaField.setLabel(annotation.value());
        phoneCaptchaField.setTooltip(annotation.tooltip());
        phoneCaptchaField.setRequired(annotation.required());
        phoneCaptchaField.setDisableAutoComplete(annotation.disableAutoComplete());
        phoneCaptchaField.setAutoFocus(annotation.autoFocus());
        phoneCaptchaField.setReadOnly(annotation.readOnly());
        phoneCaptchaField.setPlaceholder(annotation.placeholder());
        phoneCaptchaField.setCaptchaUrl(annotation.captchaUrl());
        phoneCaptchaField.setPhoneField(annotation.phoneField());
        phoneCaptchaField.setInterval(annotation.interval());

        form.addItem(phoneCaptchaField, annotation.section());
    }

    private void addEmailField(AbstractForm form, Field field, EmailField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.EmailField emailField = new com.qxcmp.framework.web.view.modules.form.field.EmailField();

        emailField.setName(field.getName());
        emailField.setLabel(annotation.value());
        emailField.setTooltip(annotation.tooltip());
        emailField.setRequired(annotation.required());
        emailField.setDisableAutoComplete(annotation.disableAutoComplete());
        emailField.setAutoFocus(annotation.autoFocus());
        emailField.setReadOnly(annotation.readOnly());
        emailField.setPlaceholder(annotation.placeholder());
        emailField.setMaxLength(annotation.maxLength());

        if (annotation.suffixList().length > 0) {
            emailField.setSuffixList(Arrays.asList(annotation.suffixList()));
        }

        form.addItem(emailField, annotation.section());
    }

    private void addPhoneField(AbstractForm form, Field field, PhoneField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.PhoneField phoneField = new com.qxcmp.framework.web.view.modules.form.field.PhoneField();

        phoneField.setName(field.getName());
        phoneField.setLabel(annotation.value());
        phoneField.setTooltip(annotation.tooltip());
        phoneField.setRequired(annotation.required());
        phoneField.setDisableAutoComplete(annotation.disableAutoComplete());
        phoneField.setAutoFocus(annotation.autoFocus());
        phoneField.setReadOnly(annotation.readOnly());
        phoneField.setPlaceholder(annotation.placeholder());
        phoneField.setMaxLength(annotation.maxLength());

        form.addItem(phoneField, annotation.section());
    }

    private void addAvatarField(AbstractForm form, Field field, AvatarField annotation) {
        final com.qxcmp.framework.web.view.modules.form.field.AvatarField avatarField = new com.qxcmp.framework.web.view.modules.form.field.AvatarField();


        avatarField.setName(field.getName());
        avatarField.setLabel(annotation.value());
        avatarField.setTooltip(annotation.tooltip());
        avatarField.setRequired(annotation.required());
        avatarField.setMaxSize(annotation.maxSize());

        form.addItem(avatarField, annotation.section());
    }
}
