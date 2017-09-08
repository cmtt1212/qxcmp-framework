package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.web.view.elements.image.AbstractImage;
import com.qxcmp.framework.web.view.elements.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageItem extends AbstractMenuItem {

    private AbstractImage image;

    public ImageItem(String source) {
        this.image = new Image(source);
    }

    public ImageItem(AbstractImage image) {
        this.image = image;
    }

    @Override
    public String getFragmentName() {
        return "item-image";
    }
}
