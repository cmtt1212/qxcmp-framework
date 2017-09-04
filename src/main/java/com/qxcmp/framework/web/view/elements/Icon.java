package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Icon extends AbstractComponent {

    /**
     * 图片元素名称
     */
    private String icon;

    public Icon(String icon) {
        this.icon = icon;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/icon";
    }

    @Override
    public String getClassName() {
        return icon + " icon";
    }
}
