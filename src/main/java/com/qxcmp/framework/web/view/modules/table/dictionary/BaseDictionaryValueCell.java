package com.qxcmp.framework.web.view.modules.table.dictionary;

import com.qxcmp.framework.web.view.elements.html.Anchor;
import com.qxcmp.framework.web.view.modules.table.AbstractTableCell;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义字典表格单元格基类
 * <p>
 * 用于将对象解析为其他格式的单元格
 * <p>
 * 所有子类可以将对象转换为自定义视图组件
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class BaseDictionaryValueCell<T> {

    protected T object;

    protected Anchor anchor;

    public BaseDictionaryValueCell(T object) {
        this.object = object;
    }

    public abstract AbstractTableCell parse();

    public BaseDictionaryValueCell<T> setUrl(String url) {
        return setUrl(url, AnchorTarget.SELF);
    }

    public BaseDictionaryValueCell<T> setUrl(String url, AnchorTarget target) {
        this.anchor = new Anchor("", url, target.toString());
        return this;
    }
}
