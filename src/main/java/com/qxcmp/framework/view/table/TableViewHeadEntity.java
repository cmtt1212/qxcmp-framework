package com.qxcmp.framework.view.table;

import lombok.Data;
import org.springframework.data.domain.Sort;

/**
 * 表格视图头部实体定义
 * <p>
 * 该实体用于前端框架渲染表格头部，每一个实体保存一个表格头部单元格所需要的所有信息
 *
 * @author aaric
 * @see TableViewEntity
 * @see TableViewFieldEntity
 */
@Data
public class TableViewHeadEntity {

    /**
     * 实体字段
     * <p>
     * 定义了该列的数据来自实体对象的哪个字段，由框架提供，一般不用填写
     */
    private String field;

    /**
     * 表头标题
     */
    private String title;

    /**
     * 表头描述
     */
    private String description;

    /**
     * 是否禁用表头排序
     * <p>
     * 如果禁用，则该列所对应的字段不支持排序功能
     */
    private Boolean disableSort;

    /**
     * 排序方式
     * <p>
     * 默认为升序
     */
    private Sort.Direction direction = Sort.Direction.ASC;

    /**
     * 排序链接
     */
    private String sortUrl;

    /**
     * 表格列的顺序，应该与对应头部定义的顺序相同
     * <p>
     * 越小的越在前面
     */
    private Integer order;
}