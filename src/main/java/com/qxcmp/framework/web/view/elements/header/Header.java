package com.qxcmp.framework.web.view.elements.header;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Header implements Component {

    /**
     * 标题文本
     */
    private String title;

    /**
     * 子标题文本
     */
    private String subTitle;

    /**
     * 对齐方式
     */
    @Builder.Default
    private TextAlignment alignment = TextAlignment.NONE;

    /**
     * 类型
     */
    @Builder.Default
    private HeaderType type = HeaderType.NORMAL;

    /**
     * 大小
     */
    @Builder.Default
    private Size size = Size.NONE;

    /**
     * 是否禁用
     */
    private boolean disabled;

    /**
     * 是否有分隔符
     */
    private boolean dividing;

    /**
     * 是否为块状
     */
    private boolean block;

    /**
     * 是否为附着状态
     */
    private boolean attached;

    /**
     * 附着方向
     */
    @Builder.Default
    private Direction attachDirection = Direction.NONE;

    /**
     * 浮动类型
     */
    @Builder.Default
    private Floating floating = Floating.NONE;

    /**
     * 颜色
     */
    @Builder.Default
    private Color color = Color.NONE;

    /**
     * 是否为反色
     */
    private boolean inverted;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/header";
    }

    @Override
    public String getClassName() {
        return "ui header" + " " + alignment.getClassName() + "" + size.getClassName() + (disabled ? "disabled" : "") + (dividing ? "dividing" : "") + (block ? "block" : "") + (attached ? "attached" : "") + " " + attachDirection.getClassName() + " " + floating.getClassName() + " " + color.getClassName() + (inverted ? "inverted" : "");
    }
}
