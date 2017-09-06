package com.qxcmp.framework.web.view.elements.list.item;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;

/**
 * 列表项组件抽象类
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractListItem extends AbstractComponent {

    /**
     * 超链接
     */
    private String url;

    /**
     * 超链接打开方式
     */
    private String urlTarget;

    /**
     * 内容垂直对齐方式
     */
    private VerticalAlignment contentAlignment = VerticalAlignment.NONE;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/list";
    }

    @Override
    public String getClassSuffix() {
        return "item";
    }

    public AbstractListItem setUrl(String url) {
        this.url = url;
        return this;
    }

    public AbstractListItem setUrlTarget(AnchorTarget target) {
        this.urlTarget = target.toString();
        return this;
    }

    public AbstractListItem setContentAlignment(VerticalAlignment contentAlignment) {
        this.contentAlignment = contentAlignment;
        return this;
    }
}
