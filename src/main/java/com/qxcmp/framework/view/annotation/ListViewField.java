package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.list.ListViewFieldTypeEnum;

import java.lang.annotation.*;

/**
 * 列表视图注解
 * <p>
 * 定义如何渲染列表视图项目
 *
 * @author aaric
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ListViewFields.class)
public @interface ListViewField {

    /**
     * @return 所属列表名称，如果为空则都属于o
     */
    String name() default "";

    /**
     * @return 实体的字段显示在列表视图的那个位置
     */
    ListViewFieldTypeEnum type();
}
