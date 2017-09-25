package com.qxcmp.framework.web.view.annotation.table;


import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.AnchorTarget;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableAction {

    /**
     * 操作名称
     */
    String value();

    /**
     * 操作Url
     * <p>
     * 实际值为 {@link EntityTable#action()} + 该值
     */
    String action();

    /**
     * 操作是否为表单提交
     */
    boolean isForm() default false;

    /**
     * 操作如果为表单提交
     * <p>
     * 指定表单的提交方式
     */
    FormMethod method() default FormMethod.POST;

    /**
     * 操作的打开方式
     */
    AnchorTarget target() default AnchorTarget.NONE;

    /**
     * 该操作是否支持
     * <p>
     * 如果支持多选操作，那么表格在有任意一行被选中的时候该操作会激活，且提交的时候会使用所有已选中的行的 entityIndex 作为集合提交
     */
    boolean supportMultiple() default false;
}
