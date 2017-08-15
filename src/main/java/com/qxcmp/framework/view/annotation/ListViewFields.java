package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 多列表视图注解，用于支持多个列表视图定义
 *
 * @author aaric
 * @see ListViewField
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListViewFields {
    ListViewField[] value();
}
