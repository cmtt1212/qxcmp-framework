package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典表格单元格
 * <p>
 * 用于将对象解析为其他格式的单元格
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractDictionaryValueCell {

    public abstract AbstractTableCell parse();
}
