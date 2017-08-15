package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.table.TableViewEntity;

import java.lang.annotation.*;

/**
 * 列表视图注解
 * <p>
 * 定义如何渲染列表视图
 *
 * @author aaric
 * @see TableViewEntity
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ListViews.class)
public @interface ListView {

    /**
     * @return 列表视图名称
     */
    String name() default "";

    /**
     * @return 列表标题
     */
    String caption() default "";

    /**
     * @return 列表基础Url
     */
    String baseUrl() default "";

    /**
     * @return 列表项目主键索引
     */
    String entityIndex() default "id";

    /**
     * @return 列表项目超链接后缀
     */
    String urlSuffix() default "";
}
