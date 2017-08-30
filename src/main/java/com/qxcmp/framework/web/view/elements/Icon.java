package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Icon extends QXCMPComponent {

    public Icon() {
        super("qxcmp/elements/icon");
    }

    /**
     * 图片元素名称
     */
    private String icon;

    @Override
    public String getClassName() {
        return icon + " icon";
    }
}
