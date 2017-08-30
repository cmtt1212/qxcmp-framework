package com.qxcmp.framework.web.view.elements.label;

import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Direction;
import com.qxcmp.framework.web.view.support.Size;
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
public class Label extends QXCMPComponent {

    /**
     * 标签文本
     */
    private String text;

    /**
     * 标签超链接
     */
    private String url;

    /**
     * 标签超链接打开方式
     */
    private String urlTarget;

    /**
     * 标签图标名称
     */
    private String icon;

    /**
     * 标签图片源
     */
    private String image;

    /**
     * 标签详情文本
     */
    private String details;

    /**
     * 是否为指针标签
     */
    private boolean pointing;

    /**
     * 指针方向
     */
    private Direction pointingDirection = Direction.NONE;

    /**
     * 是否为基本标签
     * <p>
     * 该属性将移除标签多余样式
     */
    private boolean basic;

    /**
     * 标签颜色
     */
    private Color color = Color.NONE;

    /**
     * 是否为角落标签
     * <p>
     * 角落标签需要将父容器的 position 属性设置为 relative
     */
    private boolean corner;

    /**
     * 角落位置
     */
    private Direction cornerDirection = Direction.NONE;

    /**
     * 是否为附属标签样式
     */
    private boolean tag;

    /**
     * 是否为丝带标签样式
     */
    private boolean ribbon;

    /**
     * 是否为附着标签
     */
    private boolean attached;

    /**
     * 垂直附着方向
     */
    private Direction vAttachDirection = Direction.TOP;

    /**
     * 水平附着方向
     */
    private Direction hAttachDirection = Direction.LEFT;

    /**
     * 是否为水平标签
     */
    private boolean horizontal;

    /**
     * 是否为浮动标签
     * <p>
     * 适用于徽章
     */
    private boolean floating;

    /**
     * 是否为圆形标签
     */
    private boolean circular;

    /**
     * 是否为空心圆标签
     */
    private boolean emptyCircular;

    /**
     * 标签大小
     */
    private Size size = Size.NONE;

    public Label() {
        super("qxcmp/elements/label");
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui label");

        if (pointing) {
            stringBuilder.append(" pointing");
            if (StringUtils.isNotBlank(pointingDirection.getClassName())) {
                stringBuilder.append(" ").append(pointingDirection.getClassName());
            }
        }

        if (basic) {
            stringBuilder.append(" basic");
        }

        if (StringUtils.isNotBlank(color.getClassName())) {
            stringBuilder.append(" ").append(color.getClassName());
        }

        if (corner) {
            if (StringUtils.isNotBlank(cornerDirection.getClassName())) {
                stringBuilder.append(" ").append(cornerDirection.getClassName());
            }
            stringBuilder.append(" corner");
        }

        if (tag) {
            stringBuilder.append(" tag");
        }

        if (ribbon) {
            stringBuilder.append(" ribbon");
        }

        if (attached) {
            if (StringUtils.isNotBlank(vAttachDirection.getClassName())) {
                stringBuilder.append(" ").append(vAttachDirection.getClassName());
            }
            if (StringUtils.isNotBlank(hAttachDirection.getClassName())) {
                stringBuilder.append(" ").append(hAttachDirection.getClassName());
            }
            stringBuilder.append(" attached");
        }

        if (horizontal) {
            stringBuilder.append(" horizontal");
        }

        if (floating) {
            stringBuilder.append(" floating");
        }

        if (circular) {
            if (emptyCircular) {
                stringBuilder.append(" empty");
            }
            stringBuilder.append(" circular");
        }

        if (StringUtils.isNotBlank(size.getClassName())) {
            stringBuilder.append(" ").append(size.getClassName());
        }

        return stringBuilder.toString();
    }
}
