package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;
import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

/**
 * 图片抽象类
 *
 * @author aaric
 */
@Getter
@Setter
public abstract class AbstractImage extends AbstractComponent {

    /**
     * 图片源
     */
    private String source;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 图片链接打开方式
     *
     * @see com.qxcmp.framework.web.view.support.AnchorTarget
     */
    private String urlTarget;

    /**
     * 状态：是否隐藏
     */
    private boolean hidden;

    /**
     * 状态：是否禁用
     */
    private boolean disabled;

    /**
     * 是否禁用懒加载
     * <p>
     * 默认开启图片懒加载，当浏览器滚动到图片位置时候才下载图片
     */
    private boolean disableLazyLoading;

    private AbstractImage() {
    }

    public AbstractImage(String source) {
        this();
        this.source = source;
    }

    public AbstractImage(String source, String url) {
        this(source);
        this.url = url;
    }

    public AbstractImage(String source, String url, AnchorTarget target) {
        this(source, url);
        this.urlTarget = target.toString();
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/image";
    }

    @Override
    public String getClassPrefix() {
        return "ui";
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder();

        if (hidden) {
            stringBuilder.append(" hidden");
        }

        if (disabled) {
            stringBuilder.append(" disabled");
        }

        return stringBuilder.toString();
    }

    @Override
    public String getClassSuffix() {
        return "image";
    }

    public AbstractImage setHidden() {
        this.hidden = true;
        return this;
    }

    public AbstractImage setDisabled() {
        this.disabled = true;
        return this;
    }

    public AbstractImage disableLazyLoading() {
        this.disableLazyLoading = true;
        return this;
    }
}
