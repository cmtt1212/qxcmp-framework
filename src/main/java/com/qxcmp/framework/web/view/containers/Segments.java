package com.qxcmp.framework.web.view.containers;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Segments extends AbstractSegment {

    /**
     * 是否为水平区块组
     */
    private boolean horizontal;

    /**
     * 是否为上升样式
     */
    private boolean raised;

    /**
     * 是否为堆叠样式
     */
    private boolean stacked;

    /**
     * 是否为堆积样式
     */
    private boolean piled;

    /**
     * 是否为紧凑区块组
     */
    private boolean compact;

    /**
     * 子区块
     */
    @Singular
    private List<Segment> segments;

    @Override
    public String getFragmentName() {
        return "group";
    }

    @Override
    public String getClassName() {
        StringBuilder stringBuilder = new StringBuilder("ui segments");

        if (horizontal) {
            stringBuilder.append(" horizontal");
        }

        if (raised) {
            stringBuilder.append(" raised");
        }

        if (stacked) {
            stringBuilder.append(" stacked");
        }

        if (piled) {
            stringBuilder.append(" piled");
        }

        if (compact) {
            stringBuilder.append(" compact");
        }

        return stringBuilder.toString();
    }
}
