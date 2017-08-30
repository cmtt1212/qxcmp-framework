package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Floating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Segment extends AbstractSegment {

    /**
     * 是否为基本区块
     */
    private boolean basic;

    /**
     * 是否为上升样式
     */
    private boolean raised;

    /**
     * 是否为堆叠样式
     */
    private boolean stacked;

    /**
     * 是否为高堆叠样式
     */
    private boolean tallStacked;

    /**
     * 是否为堆积样式
     */
    private boolean piled;

    /**
     * 是否为垂直区块
     */
    private boolean vertical;

    /**
     * 是否为次要区块
     */
    private boolean secondary;

    /**
     * 是否为再次要区块
     */
    private boolean tertiary;

    /**
     * 是否为禁用状态
     */
    private boolean disabled;

    /**
     * 是否为加载状态
     */
    private boolean loading;

    /**
     * 是否为颜色翻转
     */
    private boolean inverted;

    /**
     * 是否增加内边距
     */
    private boolean padded;

    /**
     * 是否增加大内边距
     */
    private boolean veryPadded;

    /**
     * 是否为紧凑区块
     */
    private boolean compact;

    /**
     * 是否为圆形区块
     */
    private boolean circular;

    /**
     * 是否为附着区块
     */
    private boolean attached;

    /**
     * 附着方向，当为附着区块的时候生效，方向仅支持 TOP, BOTTOM, NONE
     */
    @Builder.Default
    private Direction attachedDirection = Direction.NONE;

    /**
     * 区块颜色
     */
    @Builder.Default
    private Color color = Color.NONE;

    /**
     * 是否浮动
     */
    @Builder.Default
    private Floating floating = Floating.NONE;

    /**
     * 区块子元素对齐方式
     */
    @Builder.Default
    private Alignment alignment = Alignment.NONE;

    /**
     * 是否清除浮动内容
     */
    private boolean clearing;

    /**
     * 区块内容
     */
    @Singular
    private List<Component> components;

    @Override
    public String getClassName() {
        StringBuilder stringBuilder = new StringBuilder("ui segment");

        if (basic) {
            stringBuilder.append(" basic");
        }

        if (raised) {
            stringBuilder.append(" raised");
        }

        if (stacked) {
            if (tallStacked) {
                stringBuilder.append(" tall stacked");
            } else {
                stringBuilder.append(" stacked");
            }
        }

        if (piled) {
            stringBuilder.append(" piled");
        }

        if (vertical) {
            stringBuilder.append(" vertical");
        }

        if (secondary) {
            stringBuilder.append(" secondary");
        }

        if (tertiary) {
            stringBuilder.append(" tertiary");
        }

        if (disabled) {
            stringBuilder.append(" disabled");
        }

        if (loading) {
            stringBuilder.append(" loading");
        }

        if (inverted) {
            stringBuilder.append(" inverted");
        }

        if (padded) {
            if (veryPadded) {
                stringBuilder.append(" very padded");
            } else {
                stringBuilder.append(" padded");
            }
        }

        if (compact) {
            stringBuilder.append(" compact");
        }

        if (circular) {
            stringBuilder.append(" circular");
        }

        if (attached) {
            if (StringUtils.isNotBlank(attachedDirection.getClassName())) {
                stringBuilder.append(" ").append(attachedDirection.getClassName());
            }
            stringBuilder.append(" attached");
        }

        if (StringUtils.isNotBlank(color.getClassName())) {
            stringBuilder.append(" ").append(color.getClassName());
        }

        if (StringUtils.isNotBlank(floating.getClassName())) {
            stringBuilder.append(" ").append(floating.getClassName());
        }

        if (StringUtils.isNotBlank(alignment.getClassName())) {
            stringBuilder.append(" ").append(alignment.getClassName());
        }

        if (clearing) {
            stringBuilder.append(" clearing");
        }

        return stringBuilder.toString();
    }
}
