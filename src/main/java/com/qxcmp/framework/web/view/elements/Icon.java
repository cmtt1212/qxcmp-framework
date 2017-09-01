package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Icon extends AbstractComponent {

    /**
     * 图片元素名称
     */
    private String icon;

    public Icon() {
        super("qxcmp/elements/icon");
    }

    public Icon(String icon) {
        this();
        this.icon = icon;
    }

    @Override
    public String getClassName() {
        return icon + " icon";
    }
}
