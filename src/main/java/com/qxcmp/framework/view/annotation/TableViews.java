package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 多表格视图注解，用于支持多个同一实体多个表格
 *
 * @author aaric
 * @see TableView
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableViews {
    TableView[] value();
}
