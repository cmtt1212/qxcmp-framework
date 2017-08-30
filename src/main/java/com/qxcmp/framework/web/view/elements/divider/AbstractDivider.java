package com.qxcmp.framework.web.view.elements.divider;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public abstract class AbstractDivider extends QXCMPComponent {

    /**
     * 分隔符文本
     */
    private String text;

    public AbstractDivider() {
        super("qxcmp/elements/divider");
    }

    public AbstractDivider(String text) {
        this();
        this.text = text;
    }

    @Override
    public String getClassName() {
        return "ui";
    }
}
