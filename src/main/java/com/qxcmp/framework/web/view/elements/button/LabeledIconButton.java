package com.qxcmp.framework.web.view.elements.button;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class LabeledIconButton extends AbstractButton {

    /**
     * 按钮图标
     */
    private String icon;

    /**
     * 按钮文本
     */
    private String text;

    /**
     * 图标是否在右侧显示
     */
    private boolean rightIcon;

    public LabeledIconButton(String text, String icon) {
        super("label-icon");
        this.text = text;
        this.icon = icon;
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName()).append(" labeled icon");

        if (rightIcon) {
            stringBuilder.append(" right labeled");
        }

        return stringBuilder.toString();
    }
}
