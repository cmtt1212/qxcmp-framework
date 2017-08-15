package com.qxcmp.framework.view.annotation;

import java.lang.annotation.*;

/**
 * 字典视图注解
 * <p>
 * 注解定义在对象所在的类中
 * <p>
 * 用于框架快速创建字典视图实体
 *
 * @author aaric
 * @see com.qxcmp.framework.view.dictionary.DictionaryView
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DictionaryViews.class)
public @interface DictionaryView {

    /**
     * @return 字典视图名称
     */
    String name() default "";

    /**
     * @return 字典视图标题
     */
    String title() default "";
}
