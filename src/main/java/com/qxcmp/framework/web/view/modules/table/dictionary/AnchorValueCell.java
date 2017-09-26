package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.modules.table.TableData;
import com.qxcmp.framework.web.view.support.AnchorTarget;

public class AnchorValueCell extends AbstractDictionaryValueCell {

    private Anchor anchor;

    public AnchorValueCell(String text, String url) {
        super(text);
        anchor = new Anchor(text, url);
    }

    public AnchorValueCell(String text, String url, AnchorTarget target) {
        super(text);
        anchor = new Anchor(text, url, target.toString());
    }

    @Override
    public AbstractTableCell parse() {
        return new TableData(anchor);
    }
}
