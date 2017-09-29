package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.modules.table.TableData;

import java.util.Objects;

public class TextValueCell extends AbstractDictionaryValueCell {

    public TextValueCell(Object object) {
        super(object);
    }

    @Override
    public AbstractTableCell parse() {
        final TableData tableData = new TableData();

        if (Objects.nonNull(getAnchor())) {
            tableData.addComponent(new Anchor(object.toString(), getAnchor().getHref(), getAnchor().getTarget()));
        } else {
            tableData.setContent(object.toString());
        }

        return tableData;
    }
}
