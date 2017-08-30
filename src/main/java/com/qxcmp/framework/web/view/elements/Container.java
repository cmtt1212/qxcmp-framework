package com.qxcmp.framework.web.view.elements;

import com.qxcmp.framework.web.view.Component;
import com.qxcmp.framework.web.view.support.Alignment;
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

    /**
     * 是否为文本容器，该容器比一般容器具有更窄的宽度
     */
    private boolean textContainer;

    /**
     * 是否为流式容器，该容器会占满父容器的宽度
     */
    private boolean fluid;

    /**
     * 容器子元素对齐方式
     */
    @Builder.Default
    private Alignment alignment = Alignment.NONE;

    /**
     * 容器内容
     */
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
