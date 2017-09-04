package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.elements.Icon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IconButton extends AbstractButton {

    private Icon icon;

    public IconButton(String iconName) {
        this.icon = new Icon(iconName);
    }

    @Override
    public String getFragmentName() {
        return "icon";
    }

    @Override
    public String getClassName() {
        return super.getClassName() + " icon";
    }
}
