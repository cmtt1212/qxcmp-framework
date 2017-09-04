package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Floated;
import com.qxcmp.framework.web.view.support.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 按钮基本类
 *
 * @author Aaric
 */
@Getter
@Setter
public abstract class AbstractButton extends AbstractComponent {

    /**
     * 按键索引
     */
    private int tabIndex;

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
    private Floated floating = Floated.NONE;

    /**
     * 是否为附着按钮
     */
    private boolean attached;

    /**
     * 附着方向，当为附着按钮的时候生效，方向仅支持 TOP, BOTTOM, LEFT, RIGHT, NONE
     */
    private Direction attacheDirection = Direction.NONE;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/button";
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui button");

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


        stringBuilder.append(size.toString());

        stringBuilder.append(floating.toString());
        stringBuilder.append(color.toString());

        if (attached) {
            if (StringUtils.isNotBlank(attacheDirection.getClassName())) {
                stringBuilder.append(" ").append(attacheDirection.getClassName());
            }
            stringBuilder.append(" attached");
        }

        return stringBuilder.toString();
    }
}
