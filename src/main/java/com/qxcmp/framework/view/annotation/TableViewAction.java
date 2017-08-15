package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.table.TableViewActionEntity;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 表格视图操作注解
 * <p>
 * 用于快速创建表格操作定义
 *
 * @author aaric
 * @see com.qxcmp.framework.view.table.TableViewActionEntity
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableViewAction {

    /**
     * @return 操作名称
     */
    String title() default "";

    /**
     * @return 表格类型，默认为行级操作
     */
    TableViewActionEntity.ActionType type() default TableViewActionEntity.ActionType.ROW;

    /**
     * @return 是否禁用该操作
     */
    boolean disabled() default false;

    /**
     * @return 操作是否为表单
     */
    boolean isForm() default false;

    /**
     * @return 表单提交方法
     */
    HttpMethod formMethod() default HttpMethod.POST;

    /**
     * @return 超链接的打开方式，当操作不为表单的时候生效
     */
    String urlTarget() default "";

    /**
     * @return 操作目标链接前缀
     */
    String urlPrefix() default "";

    /**
     * @return 操作目标链接后缀
     */
    String urlSuffix() default "";

    /**
     * @return 操作目标链接实体索引
     */
    String urlEntityIndex() default "";

    /**
     * @return 是否禁用实体索引
     */
    boolean disableUrlEntityIndex() default false;

    /**
     * @return 是否显示确认框，当操作为表单的时候生效
     */
    boolean showDialog() default true;

    /**
     * @return 确认框标题
     */
    String dialogTitle() default "";

    /**
     * @return 确认框描述
     */
    String dialogDescription() default "";

    /**
     * 用于搜索操作
     *
     * @return 操作的查询参数
     */
    String queryPara() default "";

    /**
     * 用于排序操作
     *
     * @return 默认排序顺序
     */
    Sort.Direction direction() default Sort.Direction.ASC;
}
