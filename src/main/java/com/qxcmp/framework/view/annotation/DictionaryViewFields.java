package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 多字典视图注解，用于支持多个同一实体多个字典视图
 *
 * @author aaric
 * @see ListViewField
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictionaryViewFields {
    DictionaryViewField[] value();
}
