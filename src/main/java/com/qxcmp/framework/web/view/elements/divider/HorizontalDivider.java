package com.qxcmp.framework.web.view.elements.divider;

import com.qxcmp.framework.web.view.elements.Header;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class HorizontalDivider extends AbstractDivider {

    /**
     * 分隔符图标
     */
    private String icon;

    /**
     * 分隔符标题元素
     * <p>
     * 用于高级形式的显示
     */
    private Header header;

    /**
     * 是否为反转颜色
     */
    private boolean inverted;

    /**
     * 是否取消边距
     */
    private boolean fitted;

    /**
     * 是否为隐藏状态
     * <p>
     * 此属性将保留边距
     */
    private boolean hidden;

    /**
     * 是否增加边距
     */
    private boolean section;

    /**
     * 是否清除浮动
     */
    private boolean clearing;

    public HorizontalDivider() {
    }

    public HorizontalDivider(String text) {
        super(text);
    }

    public HorizontalDivider(String text, String icon) {
        super(text);
        this.icon = icon;
    }

    public HorizontalDivider(Header header) {
        this.header = header;
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassName());

        if (inverted) {
            stringBuilder.append(" inverted");
        }

        if (fitted) {
            stringBuilder.append(" fitted");
        }

        if (hidden) {
            stringBuilder.append(" hidden");
        }

        if (section) {
            stringBuilder.append(" section");
        }

        if (clearing) {
            stringBuilder.append(" clearing");
        }

        stringBuilder.append(" horizontal divider");

        return stringBuilder.toString();
    }
}
