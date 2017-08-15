package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.form.FormViewEntity;
import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 表单视图注解
 *
 * @author aaric
 * @see FormViewEntity
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormView {

    String caption() default "";

    String description() default "";

    String className() default "";

    String action() default "";

    String enctype() default "";

    HttpMethod method() default HttpMethod.POST;

    String submitTitle() default "";

    String submitIcon() default "";

    String dialogTitle() default "";

    String dialogDescription() default "";

    boolean disableSubmitTitle() default false;

    boolean disableSubmitIcon() default false;

    boolean disableCaption() default false;

    boolean showDialog() default false;
}
