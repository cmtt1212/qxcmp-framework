package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@Builder
public class Container implements Component {

    @Singular
    private List<Component> components;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/container";
    }

    @Override
    public String getFragmentName() {
        return "default";
    }
}
