package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 多字典视图注解，用于支持多个同一实体多个字典视图
 *
 * @author aaric
 * @see DictionaryView
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DictionaryViews {
    DictionaryView[] value();
}
