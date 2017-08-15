package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.form.CaptchaType;
import com.qxcmp.framework.view.form.InputFiledType;

import java.lang.annotation.*;

/**
 * 表单字段注解
 *
 * @author aaric
 * @see com.qxcmp.framework.view.form.FormViewFieldEntity
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormViewField {

    InputFiledType type() default InputFiledType.AUTO;

    String className() default "";

    String label() default "";

    String labelOn() default "";

    String labelOff() default "";

    String description() default "";

    String placeholder() default "";

    String accept() default "";

    String alt() default "";

    String url() default "";

    FormViewListField listFiled() default @FormViewListField;

    float step() default 0;

    String max() default "";

    String min() default "";

    int maxLength() default 0;

    int rows() default 0;

    int order() default Integer.MAX_VALUE;

    boolean autoFocus() default false;

    boolean readOnly() default false;

    boolean required() default true;

    boolean disabled() default false;

    boolean candidateI18n() default false;

    boolean consumeCandidate() default false;

    boolean enumCandidate() default false;

    Class<? extends Enum> enumCandidateClass() default Enum.class;

    String candidateValueIndex() default "";

    String candidateTextIndex() default "";

    CaptchaType captchaType() default CaptchaType.IMAGE;

    String albumFieldName() default "";
}
