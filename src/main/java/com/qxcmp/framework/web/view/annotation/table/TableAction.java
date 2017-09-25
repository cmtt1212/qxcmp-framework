package com.qxcmp.framework.web.view.annotation.table;


import com.qxcmp.framework.web.view.modules.form.FormMethod;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.Color;

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
    /**
     * 按钮颜色
     */
    Color color() default Color.NONE;

    /**
     * 是否为主要按钮
     */
    boolean primary() default false;

    /**
     * 是否为次要按钮
     */
    boolean secondary() default false;

    /**
     * 是否翻转颜色
     */
    boolean inverted() default false;

    /**
     * 是否为基本按钮
     */
    boolean basic() default false;

    /**
     * 该操作是否支持
     * <p>
     * 如果支持多选操作，那么表格在有任意一行被选中的时候该操作会激活，且提交的时候会使用所有已选中的行的 entityIndex 作为集合提交
     */
    boolean supportMultiple() default false;
}
