package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;

/**
 * 懒加载图片
 * <p>
 * 当浏览器滚动到图片位置时候才下载图片
 *
 * @author Aaric
 */
public class LazyImage extends Image {
    public LazyImage(String source) {
        super(source);
        this.setLazyLoading();
    }

    public LazyImage(String image, String url) {
        super(image, url);
        this.setLazyLoading();
    }

    public LazyImage(String image, String url, AnchorTarget target) {
        super(image, url, target);
        this.setLazyLoading();
    }
}
