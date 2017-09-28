package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.modules.table.TableData;

public class ComponentCell extends AbstractDictionaryValueCell {

    public ComponentCell(Component component) {
        super(component);
    }

    @Override
    public AbstractTableCell parse() {
        return new TableData((Component) object);
    }
}
