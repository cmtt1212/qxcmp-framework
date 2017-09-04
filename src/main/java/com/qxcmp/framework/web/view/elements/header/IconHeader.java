package com.qxcmp.framework.web.view.elements.header;

import com.qxcmp.framework.web.view.elements.icon.Icon;

/**
 * 图标抬头
 * <p>
 * 使用图标来强调内容的抬头
 *
 * @author Aaric
 */
public class IconHeader extends AbstractHeader {

    public IconHeader(String title, Icon icon) {
        super(title);
        setIcon(icon);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " icon";
    }
}
