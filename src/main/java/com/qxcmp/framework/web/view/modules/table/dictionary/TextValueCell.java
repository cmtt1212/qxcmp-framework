package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.modules.table.TableData;

import java.util.Objects;

/**
 * 文本单元格，支持超链接
 *
 * @author Aaric
 */
public class TextValueCell extends BaseDictionaryValueCell<String> {

    public TextValueCell(String object) {
        super(object);
    }

    @Override
    public AbstractTableCell parse() {
        final TableData tableData = new TableData();

        if (Objects.nonNull(getAnchor())) {
            tableData.addComponent(new Anchor(object, getAnchor().getHref(), getAnchor().getTarget()));
        } else {
            tableData.setContent(object);
        }

        return tableData;
    }
}
