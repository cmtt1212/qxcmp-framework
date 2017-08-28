package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.TextAlignment;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Container implements Component {

    private boolean textContainer;

    private boolean fluid;

    @Builder.Default
    private TextAlignment alignment = TextAlignment.NONE;

    @Singular
    private List<Component> components;

    @Override
    public String getFragmentFile() {
        return "qxcmp/elements/container";
    }

    @Override
    public String getClassName() {
        StringBuilder stringBuilder = new StringBuilder("ui container");

        if (textContainer) {
            stringBuilder.append(" text");
        }

        if (fluid) {
            stringBuilder.append(" fluid");
        }

        if (StringUtils.isNotBlank(alignment.getClassName())) {
            stringBuilder.append(" ").append(alignment.getClassName());
        }

        return stringBuilder.toString();
    }
}
