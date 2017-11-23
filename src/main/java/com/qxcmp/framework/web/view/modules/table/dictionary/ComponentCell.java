package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.modules.table.TableData;

/**
 * 自定义组件视图
 *
 * @author Aaric
 */
public class ComponentCell extends BaseDictionaryValueCell<Component> {

    public ComponentCell(Component component) {
        super(component);
    }

    @Override
    public AbstractTableCell parse() {
        return new TableData(object);
    }
}
