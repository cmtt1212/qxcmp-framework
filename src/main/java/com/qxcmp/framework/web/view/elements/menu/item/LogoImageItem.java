package com.qxcmp.framework.web.view.elements.menu.item;

import com.qxcmp.framework.core.QXCMPConfiguration;
import com.qxcmp.framework.web.view.elements.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoImageItem extends ImageItem {

    private String title;

    public LogoImageItem(String source, String title) {
        super(new Image(source, QXCMPConfiguration.QXCMP_BACKEND_URL).setRounded());
        this.title = title;
    }

    @Override
    public String getFragmentName() {
        return "item-logo";
    }

    @Override
    public String getClassSuffix() {
        return "logo " + super.getClassSuffix();
    }
}
