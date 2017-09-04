package com.qxcmp.framework.web.view.elements.icon;

import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Size;

/**
 * 方形图标
 *
 * @author Aaric
 */
public class BorderedIcon extends Icon {

    public BorderedIcon(String icon) {
        super(icon);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " bordered";
    }
}
