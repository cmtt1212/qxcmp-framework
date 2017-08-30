package com.qxcmp.framework.web.view.elements.label;

import com.google.common.collect.Lists;
import com.qxcmp.framework.web.view.support.Color;
import com.qxcmp.framework.web.view.support.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Labels extends AbstractLabel {

    /**
     * 标签组大小
     */
    private Size size = Size.NONE;

    /**
     * 标签组颜色
     */
    private Color color = Color.NONE;

    /**
     * 附属标签组
     */
    private boolean tag;

    /**
     * 圆形标签组
     */
    private boolean circular;

    /**
     * 标签列表
     */
    private List<Label> labels = Lists.newArrayList();

    public Labels() {
        super("group");
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui labels");

        if (StringUtils.isNotBlank(size.getClassName())) {
            stringBuilder.append(" ").append(size.getClassName());
        }

        if (StringUtils.isNotBlank(color.getClassName())) {
            stringBuilder.append(" ").append(color.getClassName());
        }

        if (tag) {
            stringBuilder.append(" tag");
        }

        if (circular) {
            stringBuilder.append(" circular");
        }

        return stringBuilder.toString();
    }
}
