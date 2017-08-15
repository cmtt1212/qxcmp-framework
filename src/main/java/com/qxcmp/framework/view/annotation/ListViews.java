package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 多列表视图注解，用于支持多个列表视图定义
 *
 * @author aaric
 * @see ListView
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListViews {
    ListView[] value();
}
