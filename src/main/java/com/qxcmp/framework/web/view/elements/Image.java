package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.support.AnchorTarget;
import com.qxcmp.framework.web.view.support.Floating;
import com.qxcmp.framework.web.view.support.Size;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Image extends QXCMPComponent {

    public Image() {
        super("qxcmp/elements/image");
    }

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
     * @see AnchorTarget
     */
    private String urlTarget;

    /**
     * 是否隐藏图片
     */
    private boolean hidden;

    /**
     * 是否禁用图片
     */
    private boolean disabled;

    /**
     * 是否为头像
     */
    private boolean avatar;

    /**
     * 是否显示边框
     */
    private boolean bordered;

    /**
     * 是否为满宽度
     */
    private boolean fluid;

    /**
     * 是否为圆角
     */
    private boolean rounded;

    /**
     * 是否为圆形图片
     */
    private boolean circular;

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
    private Floating floating = Floating.NONE;

    /**
     * 图片大小
     */
    private Size size = Size.NONE;

    @Override
    public String getClassName() {
        StringBuilder stringBuilder = new StringBuilder("ui image");

        if (hidden) {
            stringBuilder.append(" hidden");
        }

        if (disabled) {
            stringBuilder.append(" disabled");
        }

        if (avatar) {
            stringBuilder.append(" avatar");
        }

        if (bordered) {
            stringBuilder.append(" bordered");
        }

        if (fluid) {
            stringBuilder.append(" fluid");
        }

        if (rounded) {
            stringBuilder.append(" rounded");
        }

        if (circular) {
            stringBuilder.append(" circular");
        }

        if (centered) {
            stringBuilder.append(" centered");
        }

        if (StringUtils.isNotBlank(verticalAlignment.getClassName())) {
            stringBuilder.append(" ").append(verticalAlignment.getClassName());
        }

        if (StringUtils.isNotBlank(floating.getClassName())) {
            stringBuilder.append(" ").append(floating.getClassName());
        }

        if (StringUtils.isNotBlank(size.getClassName())) {
            stringBuilder.append(" ").append(size.getClassName());
        }

        return stringBuilder.toString();
    }
}
