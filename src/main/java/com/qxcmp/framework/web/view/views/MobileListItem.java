package com.qxcmp.framework.web.view.views;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.elements.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileListItem extends AbstractComponent {

    /**
     * 左侧文本
     */
    private String title;

    /**
     * 右侧文本 - 可选
     */
    private String subTitle;

    /**
     * 超链接
     */
    private String url;

    /**
     * 图片你- 可选
     */
    private Image image;

    /**
     * 自定义内容
     */
    private Component component;

    public MobileListItem(Component component, String url) {
        this.url = url;
        this.component = component;
    }

    public MobileListItem(Component component, String url, String subTitle) {
        this.url = url;
        this.subTitle = subTitle;
        this.component = component;
    }


    public MobileListItem(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public MobileListItem(String title, String subTitle, String url) {
        this.title = title;
        this.subTitle = subTitle;
        this.url = url;
    }

    @Override
    public String getFragmentFile() {
        return "qxcmp/views/mobile-list";
    }

    @Override
    public String getFragmentName() {
        return "item";
    }

    public MobileListItem setImage(Image image) {
        this.image = image;
        return this;
    }

    @Override
    public String getClassSuffix() {
        return "item";
    }
}
