package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.table.TableViewEntity;

import java.lang.annotation.*;

/**
 * 表格视图注解
 * <p>
 * 注解定义在表格实体对象所在的类中
 * <p>
 * 用于框架快速创建表格视图实体
 *
 * @author aaric
 * @see TableViewEntity
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TableViews.class)
public @interface TableView {

    /**
     * 表格视图名称用于同一个类生成多个表格视图的情况
     *
     * @return 表格视图名称
     */
    String name() default "";

    /**
     * 表格标题
     *
     * @return 表格标题
     */
    String caption() default "";

    /**
     * @return 实体索引
     * @see TableViewEntity#entityIndex
     */
    String entityIndex() default "";

    /**
     * @return 操作链接前缀
     * @see TableViewEntity#actionUrlPrefix
     */
    String actionUrlPrefix() default "";

    /**
     * @return 是否禁用表格操作
     * @see TableViewEntity#disableAction
     */
    boolean disableAction() default false;

    /**
     * @return 表格操作列标题
     * @see TableViewEntity#actionColumnTitle
     */
    String actionColumnTitle() default "";

    /**
     * @return 表格搜索操作定义
     */
    TableViewAction searchAction() default @TableViewAction;

    /**
     * @return 表格排序操作定义
     */
    TableViewAction sortAction() default @TableViewAction(disabled = true);

    /**
     * @return 表格新建操作定义
     */
    TableViewAction createAction() default @TableViewAction;

    /**
     * @return 表格查找操作定义
     */
    TableViewAction findAction() default @TableViewAction;

    /**
     * @return 表格更新操作定义
     */
    TableViewAction updateAction() default @TableViewAction;

    /**
     * @return 表格删除操作定义
     */
    TableViewAction removeAction() default @TableViewAction;

    /**
     * @return 表格自定义操作定义
     */
    TableViewAction[] customActions() default {};
}
