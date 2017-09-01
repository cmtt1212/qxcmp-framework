package com.qxcmp.framework.web.view.elements.menu;

import com.qxcmp.framework.web.view.elements.Icon;
import com.qxcmp.framework.web.view.elements.Image;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Getter
@Setter
public class MenuItem extends AbstractMenu {


    /**
     * 菜单项文本
     */
    private String text;

    /**
     * 菜单项超链接
     */
    private String url;

    /**
     * 超链接打开方式
     */
    private String urlTarget;

    /**
     * 可选 - 菜单项图标
     * <p>
     * 该属性会在菜单项文本附近加上图标
     */
    private Icon icon;

    /**
     * 可选 - 菜单项图片
     * <p>
     * 该属性会将菜单项渲染为图片，一般用于LOGO显示
     */
    private Image image;

    /**
     * 是否为激活状态
     */
    private boolean active;
}
