package com.qxcmp.framework.web.view.elements.list.item;

import com.qxcmp.framework.web.view.elements.image.Image;
import lombok.Getter;
import lombok.Setter;

/**
 * 图片项目
 *
 * @author Aaric
 */
@Getter
@Setter
public class ImageTextItem extends AbstractListItem {

    /**
     * 图片
     */
    private Image image;

    /**
     * 文本
     */
    private String text;

    public ImageTextItem(Image image, String text) {
        this.image = image;
        this.text = text;
    }

    public ImageTextItem(String source, String text) {
        this.image = new Image(source);
        this.text = text;
    }

    @Override
    public String getFragmentName() {
        return "item-image-text";
    }
}
