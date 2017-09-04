package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;
import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

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

    private AbstractImage() {
    }

    public AbstractImage(String source) {
        this();
        this.source = source;
    }

    public AbstractImage(String image, String url) {
        this(image);
        this.url = url;
    }

    public AbstractImage(String image, String url, AnchorTarget target) {
        this(image, url);
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
}
