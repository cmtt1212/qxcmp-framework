package com.qxcmp.framework.web.view.containers;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.QXCMPComponent;
import com.qxcmp.framework.web.view.support.Alignment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Container extends QXCMPComponent {

    public Container() {
        super("qxcmp/containers/container");
    }

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
    private Alignment alignment = Alignment.NONE;

    /**
     * 容器内容
     */
    private List<QXCMPComponent> components = Lists.newArrayList();

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
