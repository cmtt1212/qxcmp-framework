package com.qxcmp.framework.web.view.elements;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Segments extends AbstractSegment {

    private boolean horizontal;

    private boolean raised;

    private boolean stacked;

    private boolean piled;

    private boolean compact;

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
