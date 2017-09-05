package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.qxcmp.framework.web.view.elements.image.AbstractImage;
import com.qxcmp.framework.web.view.elements.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageItem extends AbstractTextDropdownItem {

    /**
     * 图片
     */
    private AbstractImage image;

    public ImageItem(String text, String source) {
        this(text, new Image(source));
    }

    public ImageItem(String text, AbstractImage image) {
        super(text);
        this.image = image;
    }

    @Override
    public String getFragmentName() {
        return "item-image";
    }
}
