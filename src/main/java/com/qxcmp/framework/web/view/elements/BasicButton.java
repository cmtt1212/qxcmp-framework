package com.qxcmp.framework.web.view.elements;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class BasicButton extends AbstractButton {

    /**
     * 按钮文本
     */
    private String text;

    @Override
    public String getFragmentName() {
        return "basic";
    }
}
