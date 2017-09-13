package com.qxcmp.framework.web.view.support.utils;

import com.google.common.base.CaseFormat;
import com.qxcmp.framework.web.view.annotation.form.Form;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
    public com.qxcmp.framework.web.view.modules.form.Form convert(Object object) {
        final com.qxcmp.framework.web.view.modules.form.Form form = new com.qxcmp.framework.web.view.modules.form.Form();

        String formName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, object.getClass().getSimpleName()).replaceAll("-form", "").replaceAll("-", ".");

        Form annotation = object.getClass().getAnnotation(Form.class);
        checkNotNull(annotation, "No Form definition in " + object.getClass().getName());

        configForm(form, annotation, formName);

        return form;
    }

    private void configForm(com.qxcmp.framework.web.view.modules.form.Form form, Form annotation, String formName) {

        form.setObject(annotation.objectName());
        form.setName(annotation.name());
        form.setMethod(annotation.method());
        form.setEnctype(annotation.enctype());
        form.setDisableAutoComplete(annotation.disableAutoComplete());
        form.setTarget(annotation.target());
        form.setSize(annotation.size());
        form.setInverted(annotation.inverted());

        if (StringUtils.isBlank(annotation.action())) {
            form.setAction("/" + formName.replaceAll("\\.", "/"));
        } else if (StringUtils.equals(annotation.action(), SELF_ACTION)) {
            form.setAction("");
        } else {
            form.setAction(annotation.action());
        }
    }
}
