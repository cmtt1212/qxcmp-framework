package com.qxcmp.framework.web.view.containers;

import com.qxcmp.framework.web.view.AbstractComponent;
import com.qxcmp.framework.web.view.support.Alignment;
import com.qxcmp.framework.web.view.support.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class AbstractGrid extends AbstractComponent {

    private static final String TEMPLATE_FILE = "qxcmp/containers/grid";

    /**
     * 对齐方式
     */
    protected Alignment alignment = Alignment.NONE;

    /**
     * 垂直对齐方式
     */
    protected VerticalAlignment verticalAlignment = VerticalAlignment.NONE;

    public AbstractGrid() {
        super(TEMPLATE_FILE);
    }

    public AbstractGrid(String fragmentName) {
        super(TEMPLATE_FILE, fragmentName);
    }

    @Override
    public String getClassName() {
        final StringBuilder stringBuilder = new StringBuilder("ui");

        if (StringUtils.isNotBlank(alignment.getClassName())) {
            stringBuilder.append(" ").append(alignment.getClassName());
        }

        if (StringUtils.isNotBlank(verticalAlignment.getClassName())) {
            stringBuilder.append(" ").append(verticalAlignment.getClassName());
        }

        return stringBuilder.toString();
    }
}
