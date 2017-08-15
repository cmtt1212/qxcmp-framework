package com.qxcmp.framework.view.table;

import lombok.Data;

/**
 * 表格视图字段实体定义
 * <p>
 * 该实体用于前端框架渲染表格字段信息，即表格列的单元格，每一个实体保存了每一列对应单元格的所有信息
 *
 * @author aaric
 * @see TableViewEntity
 * @see TableViewHeadEntity
 */
@Data
public class TableViewFieldEntity {

    /**
     * 实体字段
     * <p>
     * 定义了该列的数据来自实体对象的哪个字段，由框架提供，一般不用填写
     */
    private String field;

    /**
     * 实体字段后缀
     * <p>
     * 当实体字段对应的数据不为基本类型时，使用该值访问字段对象的所在类型
     */
    private String fieldSuffix;

    /**
     * 字段数据是否使用国际化显示
     */
    private Boolean i18n;

    /**
     * 字段是否为图片
     */
    private Boolean image;

    /**
     * 字段是否为集合类型
     * <p>
     * 如果字段为集合类型，将渲染为集合
     */
    private Boolean collection;

    /**
     * 集合字段最大渲染数量
     * <p>
     * 当字段为集合类型的时候生效
     */
    private Integer maxCollectionCount;

    /**
     * 如果字段是集合类型且集合的对象为复合对象，使用该值指定要显示的对象字段
     */
    private String collectionEntityIndex;

    /**
     * 是否渲染为超链接
     */
    private Boolean urlEnabled;

    /**
     * 超链接的打开方式
     */
    private String urlTarget;

    /**
     * 超链接实体索引
     */
    private String urlEntityIndex;

    /**
     * 超链接前缀
     * <p>
     * 默认使用 {@link TableViewEntity#actionUrlPrefix}
     */
    private String urlPrefix;

    /**
     * 超链接后缀
     * <p>
     * 默认为空
     */
    private String urlSuffix;

    /**
     * 表格列的顺序，应该与对应头部定义的顺序相同
     * <p>
     * 越小的越在前面
     */
    private int order;
}