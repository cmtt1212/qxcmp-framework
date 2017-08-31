package com.qxcmp.framework.web.view.modules.dropdown.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImageDropdownItem extends AbstractSelectionItem {

    /**
     * 图片源
     */
    private String image;

    /**
     * 是否为头像
     * <p>
     * 该属性会将图片渲染为圆形
     */
    private boolean avatar;

    @Override
    public String getFragmentName() {
        return "item-image";
    }
}
