package com.qxcmp.framework.view.table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Sort;

/**
 * 表格视图排序操作实体定义
 * 该实体包含了表格渲染操作列按钮的信息
 *
 * @author aaric
 * @see TableViewActionEntity
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TableViewSortActionEntity extends TableViewQueryParaActionEntity {

    private Sort.Direction direction = Sort.Direction.ASC;
}
