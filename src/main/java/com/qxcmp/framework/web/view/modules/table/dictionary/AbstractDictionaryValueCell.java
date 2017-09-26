package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.support.AnchorTarget;
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
public abstract class AbstractDictionaryValueCell<T> {

    protected T object;

    protected Anchor anchor;

    public AbstractDictionaryValueCell(T object) {
        this.object = object;
    }

    public abstract AbstractTableCell parse();

    public AbstractDictionaryValueCell<T> setUrl(String url) {
        return setUrl(url, AnchorTarget.SELF);
    }

    public AbstractDictionaryValueCell<T> setUrl(String url, AnchorTarget target) {
        this.anchor = new Anchor("", url, target.toString());
        return this;
    }
}
