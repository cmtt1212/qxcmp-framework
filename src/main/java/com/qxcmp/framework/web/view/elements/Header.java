package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Header extends AbstractComponent {

    public Header() {
        super("qxcmp/elements/header");
    }

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
    private HeaderType type = HeaderType.NORMAL;

    /**
     * 对齐方式
     */
    private Alignment alignment = Alignment.NONE;

    /**
     * 大小
     */
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
    private Direction attachDirection = Direction.NONE;

    /**
     * 浮动类型
     */
    private Floating floating = Floating.NONE;

    /**
     * 颜色
     */
    private Color color = Color.NONE;

    /**
     * 是否为反色
     */
    private boolean inverted;

    @Override
    public String getClassName() {
        StringBuilder stringBuilder = new StringBuilder("ui header");

        if (StringUtils.isNotBlank(alignment.toString())) {
            stringBuilder.append(" ").append(alignment.toString());
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
