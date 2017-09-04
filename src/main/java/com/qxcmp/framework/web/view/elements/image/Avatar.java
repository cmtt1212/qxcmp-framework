package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;

/**
 * 头像
 *
 * @author aaric
 */
public class Avatar extends AbstractImage {
    public Avatar(String source) {
        super(source);
    }

    public Avatar(String source, String url) {
        super(source, url);
    }

    public Avatar(String source, String url, AnchorTarget target) {
        super(source, url, target);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " avatar";
    }
}
