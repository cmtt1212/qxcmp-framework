package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.web.view.elements.image.AbstractImage;

public class LogoImageItem extends ImageItem {
    public LogoImageItem(String source) {
        super(source);
    }

    public LogoImageItem(AbstractImage image) {
        super(image);
    }

    @Override
    public String getClassSuffix() {
        return "logo " + super.getClassSuffix();
    }
}
