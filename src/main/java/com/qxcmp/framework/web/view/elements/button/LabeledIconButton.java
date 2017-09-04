package com.qxcmp.framework.web.view.elements.button;

import lombok.Getter;
import lombok.Setter;

/**
 * 带标签的图标按钮
 *
 * @author Aaric
 */
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
        this.text = text;
        this.icon = icon;
    }

    @Override
    public String getFragmentName() {
        return "label-icon";
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
