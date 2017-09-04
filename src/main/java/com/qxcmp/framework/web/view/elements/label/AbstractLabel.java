package com.qxcmp.framework.web.view.elements.label;

import com.qxcmp.framework.web.view.AbstractComponent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractLabel extends AbstractComponent {

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/label";
    }
}
