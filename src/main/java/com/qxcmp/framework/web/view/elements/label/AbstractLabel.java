package com.qxcmp.framework.web.view.elements.label;

import com.qxcmp.framework.web.view.QXCMPComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractLabel extends QXCMPComponent{

    private static final String TEMPLATE_FILE = "qxcmp/elements/label";

    public AbstractLabel() {
        this("default");
    }

    public AbstractLabel(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }
}
