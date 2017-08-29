package com.qxcmp.framework.web.view.collections;

import com.qxcmp.framework.web.view.elements.Image;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class MenuItem extends AbstractMenu {

    private String text;

    private String url;

    private String urlTarget;

    private Image image;

    @Override
    public String getFragmentName() {
        return "item";
    }
}
