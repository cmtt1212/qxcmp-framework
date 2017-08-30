package com.qxcmp.framework.web.view.elements.button;

import com.qxcmp.framework.web.view.elements.Icon;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IconButton extends AbstractButton {

    private Icon icon;

    public IconButton(String iconName) {
        super("icon");
        final Icon icon = new Icon();
        icon.setIcon(iconName);
        this.icon = icon;
    }

    @Override
    public String getClassName() {
        return super.getClassName() + " icon";
    }
}
