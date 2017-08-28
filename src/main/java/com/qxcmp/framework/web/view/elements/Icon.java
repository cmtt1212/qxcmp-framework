package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Icon implements Component {

    private String icon;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/icon";
    }

    @Override
    public String getClassName() {
        return icon + " icon";
    }
}
