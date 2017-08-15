package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 表单列表字段注解
 * <p>
 * 用于表单字段是列表的情况，可以动态的添加和删除列表项目
 *
 * @author aaric
 * @see com.qxcmp.framework.view.form.ListFieldEntity
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormViewListField {

    /**
     * 表单列表字段需要显示的字段域
     *
     * @return 表单列表字段需要显示的字段域
     */
    String[] fields() default {};

    String[] labels() default {};

    /**
     * 表单列表字段的集合类型是否为原始类型
     *
     * @return 默认为否
     */
    boolean isRawType() default false;
}
