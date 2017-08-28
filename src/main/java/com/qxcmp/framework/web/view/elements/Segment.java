package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Floating;
import com.qxcmp.framework.web.view.support.TextAlignment;
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

    private boolean basic;

    private boolean raised;

    private boolean stacked;

    private boolean tallStacked;

    private boolean piled;

    private boolean vertical;

    private boolean secondary;

    private boolean tertiary;

    private boolean disabled;

    private boolean loading;

    private boolean inverted;

    private boolean padded;

    private boolean veryPadded;

    private boolean compact;

    private boolean circular;

    private boolean attached;

    @Builder.Default
    private Direction attachedDirection = Direction.NONE;

    @Builder.Default
    private Color color = Color.NONE;

    @Builder.Default
    private Floating floating = Floating.NONE;

    @Builder.Default
    private TextAlignment alignment = TextAlignment.NONE;

    private boolean clearing;

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
