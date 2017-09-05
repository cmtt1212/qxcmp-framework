package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.elements.icon.Icon;
import lombok.Getter;
import lombok.Setter;

/**
 * 图标按钮
 *
 * @author Aaric
 */
@Getter
@Setter
public class IconButton extends AbstractButton {

    private Icon icon;

    public IconButton(String iconName) {
        this.icon = new Icon(iconName);
    }

    public IconButton(Icon icon) {
        this.icon = icon;
    }

    @Override
    public String getFragmentName() {
        return "icon";
    }

    @Override
    public String getClassContent() {
        return super.getClassContent() + " icon";
    }
}
