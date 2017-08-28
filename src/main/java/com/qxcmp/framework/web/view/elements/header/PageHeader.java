package com.qxcmp.framework.web.view.elements.header;

import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Floating;
import com.qxcmp.framework.web.view.support.TextAlignment;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class PageHeader extends AbstractHeader {

    private String text;

    @Builder.Default
    private TextAlignment alignment = TextAlignment.LEFT;

    @Builder.Default
    private PageHeaderType type = PageHeaderType.H1;

    private boolean disabled;

    private boolean dividing;

    private boolean block;

    private boolean attached;

    @Builder.Default
    private Direction attachDirection = Direction.NONE;

    @Builder.Default
    private Floating floating = Floating.NONE;

    @Builder.Default
    private Color color = Color.NONE;

    private boolean inverted;

    @Override
    public String getFragmentName() {
        return "page-header";
    }
}
