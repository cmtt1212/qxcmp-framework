package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 多表格视图字段注解，用于支持多个同一实体多个表格
 *
 * @author aaric
 * @see TableViewField
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableViewFields {
    TableViewField[] value();
}
