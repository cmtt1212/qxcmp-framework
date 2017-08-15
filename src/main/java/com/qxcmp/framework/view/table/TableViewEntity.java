package com.qxcmp.framework.view.table;

import com.google.common.collect.Lists;
import com.qxcmp.framework.view.pagination.Pagination;
import lombok.Data;

import java.util.List;

/**
 * 表格视图实体定义
 * <p>
 * 该实体用于前端框架渲染，以共同的方式储存了前端框架渲染所需要的所有信息。
 * <p>
 * 该实体可以作为前端渲染的中间件，可以为不同的前端框架提供支持
 *
 * @param <T> 表格渲染的集合对象类型
 *
 * @author aaric
 * @see TableViewHeadEntity
 * @see TableViewFieldEntity
 */
@Data
public class TableViewEntity<T> {

    /**
     * 表格标题
     */
    private String caption;

    /**
     * 实体索引
     * <p>
     * 该值必须为表格关联实体对象存在的字段名称
     * <p>
     * 实体索引用于查找具体的实体对象，也用在生成表格操作的Url中
     * <p>
     * 索引的默认值为 {@code id}，客户端需要确保实体的该字段存在
     */
    private String entityIndex;

    /**
     * 操作Url前缀
     * <p>
     * 表格操作Url的模式为 {@code actionUrlPrefix} + {@code entityIndex} + {@code actionUrlSuffix}
     * <p>
     * 该值为默认的操作Url前缀，可以通过每个具体的操作覆盖该值
     */
    private String actionUrlPrefix;

    /**
     * 是否禁用表格操作
     * <p>
     * 表格操作默认开启CRUD操作，该字段方便快速关闭所有表格操作
     */
    private boolean disableAction;

    /**
     * 表格操作列标题
     * <p>
     * 操作列在禁用表格操作或者没有任何可用操作的时候将不显示
     */
    private String actionColumnTitle;

    /**
     * 表格搜索操作定义
     * <p>
     * 搜索操作为表级操作，一般为表单请求
     *
     * @see TableViewQueryParaActionEntity
     */
    private TableViewQueryParaActionEntity searchAction;

    /**
     * 表格排序操作定义
     * <p>
     * 排序操作为表级操作，会在表格每一列上面生成排序选项
     *
     * @see TableViewSortActionEntity
     */
    private TableViewSortActionEntity sortAction;

    /**
     * 表格表级操作定义
     * <p>
     * 默认的表级操作有
     * <ol>
     * <li>新建操作</li>
     * </ol>
     *
     * @see TableViewActionEntity
     */
    private List<TableViewActionEntity> tableActions = Lists.newArrayList();

    /**
     * 表格行级操作定义
     * <p>
     * 默认的行级操作有
     * <ol>
     * <li>查找操作</li>
     * <li>更新操作</li>
     * <li>删除操作</li>
     * </ol>
     *
     * @see TableViewActionEntity
     */
    private List<TableViewActionEntity> rowActions = Lists.newArrayList();

    /**
     * 表头定义
     * <p>
     * 定义表格头部的渲染信息
     *
     * @see TableViewHeadEntity
     */
    private List<TableViewHeadEntity> heads = Lists.newArrayList();

    /**
     * 表字段定义
     * <p>
     * 定义表格每一列的渲染信息
     *
     * @see TableViewFieldEntity
     */
    private List<TableViewFieldEntity> fields = Lists.newArrayList();

    /**
     * 表格实体元素
     */
    private List<T> items = Lists.newArrayList();

    /**
     * 表格分页对象
     * <p>
     * 用于渲染表格分页组件
     *
     * @see Pagination
     */
    private Pagination pagination;
}