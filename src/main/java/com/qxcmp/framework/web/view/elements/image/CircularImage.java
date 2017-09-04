package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;

/**
 * 圆形图片
 *
 * @author aaric
 */
public class CircularImage extends Image {
    public CircularImage(String source) {
        super(source);
    }

    public CircularImage(String source, String url) {
        super(source, url);
    }

    public CircularImage(String source, String url, AnchorTarget target) {
        super(source, url, target);
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " circular";
    }
}
