package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.*;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
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
     * 可选：图标
     */
    private Icon icon;

    /**
     * 可选：图片
     */
    private Image image;

    /**
     * 类型
     */
    @Builder.Default
    private HeaderType type = HeaderType.NORMAL;

    /**
     * 对齐方式
     */
    @Builder.Default
    private TextAlignment alignment = TextAlignment.NONE;

    /**
     * 大小
     */
    @Builder.Default
    private Size size = Size.NONE;

    /**
     * 是否强调显示图标
     */
    private boolean iconHeader;

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
        StringBuilder stringBuilder = new StringBuilder("ui header");

        if (StringUtils.isNotBlank(alignment.getClassName())) {
            stringBuilder.append(" ").append(alignment.getClassName());
        }

        if (StringUtils.isNotBlank(size.getClassName())) {
            stringBuilder.append(" ").append(size.getClassName());
        }

        if (iconHeader) {
            stringBuilder.append(" icon");
        }

        if (disabled) {
            stringBuilder.append(" disabled");
        }

        if (dividing) {
            stringBuilder.append(" dividing");
        }

        if (block) {
            stringBuilder.append(" block");
        }

        if (attached) {
            if (StringUtils.isNotBlank(attachDirection.getClassName())) {
                stringBuilder.append(" ").append(attachDirection.getClassName());
            }
            stringBuilder.append(" attached");
        }

        if (StringUtils.isNotBlank(floating.getClassName())) {
            stringBuilder.append(" ").append(floating.getClassName());
        }

        if (StringUtils.isNotBlank(color.getClassName())) {
            stringBuilder.append(" ").append(color.getClassName());
        }

        if (inverted) {
            stringBuilder.append(" inverted");
        }

        return stringBuilder.toString();
    }
}
