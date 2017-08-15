package com.qxcmp.framework.view.table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表格视图带查询参数实体定义
 * 该实体包含了表格渲染操作列按钮的信息
 *
 * @author aaric
 * @see TableViewActionEntity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableViewQueryParaActionEntity extends TableViewActionEntity {

    private String queryParameter;
}
