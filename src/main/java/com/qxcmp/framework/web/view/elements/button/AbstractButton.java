package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Floating;
import com.qxcmp.framework.web.view.support.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AbstractButton extends QXCMPComponent {

    private static final String TEMPLATE_FILE = "qxcmp/elements/button";

    public AbstractButton() {
        this("default");
    }

    public AbstractButton(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }

    /**
     * 按键索引
     */
    private int tabIndex;

    /**
     * 是否为主要按钮
     */
    private boolean primary;

    /**
     * 是否为次要按钮
     */
    private boolean secondary;

    /**
     * 是否为基本按钮
     * <p>
     * 该属性将移除多余的强调内容
     */
    private boolean basic;

    /**
     * 是否为紧凑按钮
     */
    private boolean compact;

    /**
     * 是否占满父元素宽度
     */
    private boolean fluid;

    /**
     * 是否为圆形按钮
     */
    private boolean circular;

    /**
     * 是否翻转颜色
     */
    private boolean inverted;

    /**
     * 是否为激活状态
     */
    private boolean active;

    /**
     * 是否为禁用状态
     */
    private boolean disabled;

    /**
     * 是否为加载状态
     */
    private boolean loading;

    /**
     * 按钮颜色
     */
    private Color color = Color.NONE;

    /**
     * 按钮大小
     */
    private Size size = Size.NONE;

    /**
     * 浮动类型
     */
    private Floating floating = Floating.NONE;

    /**
     * 是否为附着按钮
     */
    private boolean attached;

    /**
     * 附着方向，当为附着按钮的时候生效，方向仅支持 TOP, BOTTOM, LEFT, RIGHT, NONE
     */
    private Direction attacheDirection = Direction.NONE;

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui button");

        if (primary) {
            stringBuilder.append(" primary");
        }

        if (secondary) {
            stringBuilder.append(" secondary");
        }

        if (basic) {
            stringBuilder.append(" basic");
        }

        if (compact) {
            stringBuilder.append(" compact");
        }

        if (fluid) {
            stringBuilder.append(" fluid");
        }

        if (circular) {
            stringBuilder.append(" circular");
        }

        if (inverted) {
            stringBuilder.append(" inverted");
        }

        if (active) {
            stringBuilder.append(" active");
        }

        if (disabled) {
            stringBuilder.append(" disabled");
        }

        if (loading) {
            stringBuilder.append(" loading");
        }

        if (StringUtils.isNotBlank(color.getClassName())) {
            stringBuilder.append(" ").append(color.getClassName());
        }

        if (StringUtils.isNotBlank(size.getClassName())) {
            stringBuilder.append(" ").append(size.getClassName());
        }

        if (StringUtils.isNotBlank(floating.getClassName())) {
            stringBuilder.append(" ").append(floating.getClassName());
        }

        if (attached) {
            if (StringUtils.isNotBlank(attacheDirection.getClassName())) {
                stringBuilder.append(" ").append(attacheDirection.getClassName());
            }
            stringBuilder.append(" attached");
        }

        return stringBuilder.toString();
    }
}
