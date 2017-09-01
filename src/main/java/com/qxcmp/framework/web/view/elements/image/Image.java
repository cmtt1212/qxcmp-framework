package com.qxcmp.framework.web.view.elements.image;

import com.qxcmp.framework.view.component.AnchorTarget;
import com.qxcmp.framework.web.view.support.Floated;
import com.qxcmp.framework.web.view.support.Size;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image extends AbstractImage {

    /**
     * 样式：是否显示边框
     */
    private boolean bordered;

    /**
     * 样式：是否占满父容器
     */
    private boolean fluid;

    /**
     * 是否为圆角
     */
    private boolean rounded;

    /**
     * 是否居中显示
     */
    private boolean centered;

    /**
     * 垂直对齐方式
     */
    private VerticalAlignment verticalAlignment = VerticalAlignment.NONE;

    /**
     * 浮动类型
     */
    private Floated floated = Floated.NONE;

    /**
     * 图片大小
     */
    private Size size = Size.NONE;

    public Image(String source) {
        super(source);
    }

    public Image(String image, String url) {
        super(image, url);
    }

    public Image(String image, String url, AnchorTarget target) {
        super(image, url, target);
    }

    @Override
    public String getClassContent() {
        final StringBuilder stringBuilder = new StringBuilder(super.getClassContent());

        if (bordered) {
            stringBuilder.append(" bordered");
        }

        if (fluid) {
            stringBuilder.append(" fluid");
        }

        if (rounded) {
            stringBuilder.append(" rounded");
        }

        if (centered) {
            stringBuilder.append(" centered");
        }

        stringBuilder.append(verticalAlignment.toString()).append(floated.toString()).append(size.toString());

        return stringBuilder.toString();
    }
}
