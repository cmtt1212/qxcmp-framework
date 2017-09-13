package com.qxcmp.framework.web.view.support.utils;

import com.google.common.base.CaseFormat;
import com.qxcmp.framework.web.view.annotation.form.Form;
import com.qxcmp.framework.web.view.annotation.form.TextInputField;
import com.qxcmp.framework.web.view.modules.form.AbstractForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class FormHelper {

    public static final String SELF_ACTION = "$SELF";

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

        return form;
    }

    private void configFormField(AbstractForm form, Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof TextInputField) {
                    addTextInputField(form, field, (TextInputField) annotation);
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
}
