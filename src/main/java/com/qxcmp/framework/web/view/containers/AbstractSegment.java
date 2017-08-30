package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Getter
@Setter
public abstract class AbstractSegment extends QXCMPComponent {

    private static final String TEMPLATE_FILE = "qxcmp/containers/segment";

    public AbstractSegment() {
        super(TEMPLATE_FILE);
    }

    public AbstractSegment(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
