package com.qxcmp.framework.view.annotation;

import com.qxcmp.framework.view.table.TableViewFieldEntity;
import com.qxcmp.framework.view.table.TableViewHeadEntity;
import org.springframework.data.domain.Sort;

import java.lang.annotation.*;

/**
 * 表格视图字段配置注解，用于生成表格视图头部实体和表格视图字段实体
 * <p>
 * 需要定义在实体字段上
 *
 * @author aaric
 * @see TableViewHeadEntity
 * @see TableViewFieldEntity
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(TableViewFields.class)
public @interface TableViewField {

    /**
     * 该字段属于哪个表格视图
     *
     * @return 表格视图名称
     */
    String name() default "";

    /**
     * @return 表头标题
     */
    String title() default "";

    /**
     * @return 表头描述
     */
    String description() default "";

    /**
     * @return 实体字段后缀 {@link TableViewFieldEntity#fieldSuffix}
     * @see TableViewFieldEntity#fieldSuffix
     */
    String fieldSuffix() default "";

    /**
     * @return 是否禁用排序
     */
    boolean disableSort() default false;

    /**
     * @return 搜索功能是否匹配该字段
     */
    boolean disableSearch() default false;

    /**
     * @return 排序方向
     */
    Sort.Direction sortDirection() default Sort.Direction.ASC;

    /**
     * @return 排序Url前缀
     */
    String sortUrlPrefix() default "";

    /**
     * @return 排序Url后缀
     */
    String sortUrlSuffix() default "";

    /**
     * @return 排序Url查询参数
     */
    String sortUrlQueryPara() default "";

    /**
     * @return 是否使用国际化显示
     */
    boolean useI18n() default false;

    /**
     * @return 是否为图片
     */
    boolean isImage() default false;

    /**
     * @return 是否为集合类型
     */
    boolean isCollection() default false;

    /**
     * @return 最大集合渲染个数
     */
    int maxCollectionCount() default 5;

    /**
     * @return 集合实体索引
     */
    String collectionEntityIndex() default "";

    /**
     * @return 是否渲染为超链接
     */
    boolean urlEnabled() default false;

    /**
     * @return 超链接的打开方式
     */
    String urlTarget() default "";

    /**
     * @return 超链接实体索引
     */
    String urlEntityIndex() default "";

    /**
     * @return 超链接前缀
     */
    String urlPrefix() default "";

    /**
     * @return 超链接后缀
     */
    String urlSuffix() default "";

    /**
     * @return 表格列的顺序
     */
    int order() default Integer.MAX_VALUE;
}
