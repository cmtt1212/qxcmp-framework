package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;

public class CircularImage extends Image {
    public CircularImage(String source) {
        super(source);
    }

    public CircularImage(String image, String url) {
        super(image, url);
    }

    public CircularImage(String image, String url, AnchorTarget target) {
        super(image, url, target);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " circular";
    }
}
