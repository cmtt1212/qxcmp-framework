package com.qxcmp.framework.web.view.modules.dropdown.item;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
public abstract class AbstractItem extends QXCMPComponent {

    public AbstractItem() {
        super("qxcmp/modules/dropdown");
    }
}
