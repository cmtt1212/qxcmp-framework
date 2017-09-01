package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;

public class Avatar extends AbstractImage {
    public Avatar(String source) {
        super(source);
    }

    public Avatar(String image, String url) {
        super(image, url);
    }

    public Avatar(String image, String url, AnchorTarget target) {
        super(image, url, target);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " avatar";
    }
}
