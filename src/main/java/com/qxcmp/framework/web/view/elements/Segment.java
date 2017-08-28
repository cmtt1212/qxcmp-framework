package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Segment implements Component {

    @Singular
    private List<Component> components;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/segment";
    }
}
