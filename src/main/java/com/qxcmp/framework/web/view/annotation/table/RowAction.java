package com.qxcmp.framework.web.view.annotation.table;


import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.AnchorTarget;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RowAction {

    /**
     * 操作名称
     */
    String value();

    /**
     * 操作Url
     * <p>
     * 实际值为 {@link EntityTable#action()} + {@link EntityTable#entityIndex()} + 该值
     */
    String action();

    /**
     * 默认为超链接
     * <p>
     * 操作如果为表单提交
     * <p>
     * 指定表单的提交方式
     */
    FormMethod method() default FormMethod.NONE;

    /**
     * 操作的打开方式
     */
    AnchorTarget target() default AnchorTarget.NONE;

}
