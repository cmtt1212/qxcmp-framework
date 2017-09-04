package com.qxcmp.framework.web.view.elements.icon;

import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Size;

/**
 * 圆形图标
 *
 * @author Aaric
 */
public class CircularIcon extends Icon {

    public CircularIcon(String icon) {
        super(icon);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " circular";
    }
}
