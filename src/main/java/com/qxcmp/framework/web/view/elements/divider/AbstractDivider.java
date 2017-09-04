package com.qxcmp.framework.web.view.elements.divider;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDivider extends AbstractComponent {

    /**
     * 分隔符文本
     */
    private String text;

    public AbstractDivider() {
    }

    public AbstractDivider(String text) {
        this.text = text;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/divider";
    }

    @Override
    public String getClassName() {
        return "ui";
    }
}
