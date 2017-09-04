package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 懒加载图片
 * <p>
 * 当浏览器滚动到图片位置时候才下载图片
 *
 * @author Aaric
 */
public class LazyImage extends Image {

    /**
     * 懒加载图片ID
     * <p>
     * 用于初始化懒加载
     */
    private String id = "lazy-image-" + RandomStringUtils.randomAlphanumeric(10);

    public LazyImage(String source) {
        super(source);
    }

    public LazyImage(String image, String url) {
        super(image, url);
    }

    public LazyImage(String image, String url, AnchorTarget target) {
        super(image, url, target);
    }

    @Override
    public String getFragmentName() {
        return "lazy-image";
    }
}
